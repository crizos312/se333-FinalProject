---
agent: "agent"
tools: ['maven_integration', 'test_generation', 'missing_coverage', 'git_status', 'git_add_all', 'git_commit', 'git_push', 'git_pull_request']
description: "description of the tool"
model: 'Grok'
---
## Follow instruction below: ##
1. Find all the source code I want to test in 'codebase\codebase' directory.
2. Execute maven tests using 'maven_integration' tool.
3. Get jacoco.xlm using some 'findpath' then parse jacoco.xml to find uncovered classes using 'missing_coverage' tool.
4. Use the missing coverage to write better tests to cover it using 'test_generation' tool.
5. Use Git tools to manage code changes 'git_add_all', 'git_commit', 'git_push'.
6. Repeat until coverage is 100%