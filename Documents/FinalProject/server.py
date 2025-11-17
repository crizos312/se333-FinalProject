from fastmcp import FastMCP
import subprocess
import os
import json
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
def spec_test_generation(spec_path: str, output_path: str):
    import json, os
    with open(spec_path, encoding="utf-8") as f:
        spec = json.load(f)

    pkg = spec.get("package")
    cls = spec.get("testClass", "GeneratedSpecTests")
    tests = spec.get("tests", [])

    lines = []
    if pkg:
        lines.append(f"package {pkg};")
    lines += [
        "import org.junit.Test;",
        "import static org.junit.Assert.*;",
        f"public class {cls} {{"
    ]

    for i, t in enumerate(tests, 1):
        name = t.get("name", f"test{i}")
        args = ", ".join(map(str, t.get("args", [])))
        expr = f"{t.get('invoke')}({args})"
        expect = t.get("expect")
        lines.append(f"    @Test public void {name}() {{")
        lines.append(f"        assertEquals({expect}, {expr});")
        lines.append("    }")

    lines.append("}")

    os.makedirs(output_path, exist_ok=True)
    out_file = os.path.join(output_path, cls + ".java")
    with open(out_file, "w", encoding="utf-8") as f:
        f.write("\n".join(lines))

    return f"spec_tests_generated:{out_file}"


@mcp.tool()
def test_generation(source_path: str, output_path: str):
    try:
        def sanitize_identifier(s: str) -> str:
            import re
            s = re.sub(r"[^0-9A-Za-z_]", "_", s)
            if not s:
                s = "x"
            if s[0].isdigit():
                s = "_" + s
            return s

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
        used_names = set()
        idx = 1
        for m in methods:
            raw_name = m.split("(")[0].split()[-1]
            safe = sanitize_identifier(raw_name)
            method_name = f"test_{safe}"
            # ensure uniqueness
            while method_name in used_names:
                idx += 1
                method_name = f"test_{safe}_{idx}"
            used_names.add(method_name)
            test = f"    @Test\n    public void {method_name}() {{}}\n"
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