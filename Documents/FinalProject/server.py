from fastmcp import FastMCP
import subprocess
import os
import xml.etree.ElementTree as ET

mcp = FastMCP("My MCP Server")

#Core testing
@mcp.tool()
def maven_integration(project_path: str):
    try:
        result = subprocess.run(
            ["mvn", "clean", "test"],
            cwd=project_path,
            capture_output=True,
            text=True,
            shell=False
        )
        status = "success" if result.returncode == 0 else f"failure:{result.returncode}"
        return f"maven_test_{status}\n{result.stdout}\n{result.stderr}"
    except Exception as e:
        return f"error: {e}"

@mcp.tool()
def test_generation(source_path: str, output_path: str):
    try:
        methods = []
        for root, _, files in os.walk(source_path):
            for f in files:
                if f.endswith(".java"):
                    with open(os.path.join(root, f), encoding="utf-8", errors="ignore") as src:
                        for line in src:
                            line = line.strip()
                            if line.startswith("public") and "(" in line and ")" in line and "class" not in line:
                                methods.append(line)
        generated = []
        for m in methods:
            name = m.split("(")[0].split()[-1]
            test = f"    @Test\n    public void test_{name}() {{}}\n"
            generated.append(test)
        os.makedirs(output_path, exist_ok=True)
        with open(os.path.join(output_path, "GeneratedTests.java"), "w", encoding="utf-8") as f:
            f.write("import org.junit.Test;\npublic class GeneratedTests {\n")
            for t in generated:
                f.write(t + "\n")
            f.write("}\n")
        return "tests_generated"
    except Exception as e:
        return f"error: {e}"

@mcp.tool()
def missing_coverage(report_path: str):
    try:
        tree = ET.parse(report_path)
        root = tree.getroot()
        uncovered = []
        for pkg in root.findall(".//package"):
            for cls in pkg.findall("class"):
                for counter in cls.findall("counter"):
                    if counter.get("type") == "LINE":
                        missed = int(counter.get("missed"))
                        if missed > 0:
                            uncovered.append(cls.get("name"))
        return uncovered
    except Exception as e:
        return f"error: {e}"

#Git tools
def run_git(cmd):
    try:
        out = subprocess.check_output(cmd, stderr=subprocess.STDOUT, shell=True, text=True)
        return out.strip()
    except subprocess.CalledProcessError as e:
        return e.output.strip()

@mcp.tool()
def git_status():
    return run_git("git status --short")

@mcp.tool()
def git_add_all():
    return run_git("git add -A")

@mcp.tool()
def git_commit(message: str):
    return run_git(f'git commit -m "{message}"')

@mcp.tool()
def git_push(remote: str = "origin"):
    return run_git(f"git push {remote}")

@mcp.tool()
def git_pull_request(base: str, title: str, body: str):
    import json
    data = json.dumps({
        "title": title,
        "body": body,
        "base": base,
        "head": run_git("git rev-parse --abbrev-ref HEAD")
    })
    try:
        result = subprocess.check_output(
            f'gh pr create --title "{title}" --body "{body}" --base "{base}"',
            stderr=subprocess.STDOUT, shell=True, text=True
        )
        return result.strip()
    except subprocess.CalledProcessError as e:
        return e.output.strip()



if __name__ == "__main__":
    mcp.run(transport="sse")