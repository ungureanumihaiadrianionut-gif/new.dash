
#!/bin/bash
if command -v gradle >/dev/null 2>&1; then
  gradle "$@"
else
  echo "Gradle not found; use GitHub Actions' Gradle action instead."
  exit 1
fi
