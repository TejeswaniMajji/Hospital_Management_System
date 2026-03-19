#!/bin/bash

# Hospital Management System - Run Script

echo "🏥 Initializing Hospital Management System..."

# Ensure we are in the script's directory
cd "$(dirname "$0")"

# make the JVM prefer IPv4 addresses; avoids IPv6 lookup issues when testing locally
export JAVA_TOOL_OPTIONS="-Djava.net.preferIPv4Stack=true"

# if a database URL is supplied, try to resolve it over IPv4 before starting
if [ -n "$SPRING_DATASOURCE_URL" ]; then
    # crude parsing of jdbc url to extract host and port
    host=$(echo "$SPRING_DATASOURCE_URL" | sed -n 's#.*//\([^:/]*\):\([0-9]*\).*#\1#p')
    port=$(echo "$SPRING_DATASOURCE_URL" | sed -n 's#.*//[^:/]*:\([0-9]*\).*#\1#p')
    if [ -n "$host" ] && [ -n "$port" ]; then
        echo "🔍 checking IPv4 connectivity to $host:$port"
        nc -4 -zv "$host" "$port" || \
            echo "⚠️  unable to reach $host:$port – verify network/DNS/security rules"
    fi
fi

# Check if Java is installed
if ! command -v java >/dev/null 2>&1; then
    echo "❌ Error: Java is not installed or not in PATH."
    echo "Please install Java 17 or higher and ensure it is in your PATH."
    echo "For macOS, you can use Homebrew: brew install openjdk@17"
    exit 1
fi

# Check for Java 17 (Recommended)
JAVA_VERSION=$(java -version 2>&1 | awk 'NR==1{gsub(/"/,""); print $2}')
if [[ ! "$JAVA_VERSION" =~ ^17 ]]; then
    echo "⚠️  Note: Java 17 is recommended. Current version:"
    java -version 2>&1 | head -n 1
    echo "Using Java version: $JAVA_VERSION.  Some features may not work as expected."
fi

# Check if Maven is installed
if ! command -v mvn >/dev/null 2>&1; then
    echo "❌ Error: Maven is not installed or not in PATH."
    echo "Please install Maven and ensure it is in your PATH."
    echo "For macOS, you can use Homebrew: brew install maven"
    exit 1
fi

echo "🔨 Building project (skipping tests for speed)..."
# respect PORT env for maven run if provided
mvn clean install -DskipTests

# Check if the build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful."
    echo "🚀 Starting application..."
    if [ -n "$PORT" ]; then
        echo "📡 Using port from PORT environment variable: $PORT"
    fi
    echo "🌐 Once started, go to: http://localhost:${PORT:-8080}/hospital/auth/login"
    # pass IPv4 preference again when launching via Maven
    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Djava.net.preferIPv4Stack=true"
else
    echo "❌ Build failed. Please check the output above for error messages."
    exit 1
fi