# ⚠️ Java Installation - Fix for Homebrew

## 🔴 The Problem

You tried: `brew install java@17`

**Error:** `No available formula with the name "java@17". Did you mean javacc?`

The issue is that Homebrew **does not have a formula called `java@17`**. 

---

## ✅ The Solution

Use **`openjdk@17`** instead of `java@17`:

```bash
brew install openjdk@17
```

---

## 🚀 Complete Fix - Run These 3 Commands

### Command 1: Install OpenJDK 17
```bash
brew install openjdk@17
```

### Command 2: Create symlink for Java command
```bash
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java
```

### Command 3: Verify Installation
```bash
java -version
```

**Expected Output:**
```
openjdk version "17.x.x" 
OpenJDK Runtime Environment
```

---

## 📋 Alternative: Use Latest OpenJDK (Recommended)

If you want the latest stable Java version:

```bash
# Install latest OpenJDK (currently Java 21+)
brew install openjdk

# Create symlink
sudo ln -sfn $(brew --prefix)/opt/openjdk/bin/java /usr/local/bin/java

# Verify
java -version
```

---

## 🔍 All Available Java Versions in Homebrew

```
✅ openjdk        (Latest - currently Java 21+)
✅ openjdk@21     (Java 21)
✅ openjdk@17     (Java 17)
✅ openjdk@11     (Java 11)
✅ openjdk@8      (Java 8)
```

---

## ⚡ Quick Fix - Copy & Paste

**Just run this one-liner:**

```bash
brew install openjdk@17 && sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java && java -version
```

---

## 📝 Step-by-Step Instructions

### Step 1: Uninstall Wrong Version (if needed)
```bash
# If you accidentally installed javacc, remove it:
brew uninstall javacc
```

### Step 2: Install Correct Java
```bash
brew install openjdk@17
```

**You'll see output like:**
```
==> Downloading https://ghcr.io/v2/homebrew/core/openjdk@17/...
==> Installing openjdk@17...
🍺 /usr/local/opt/openjdk@17 is not symlinked into /usr/local
To link this version, run:
  sudo ln -sfn /usr/local/opt/openjdk@17 /usr/local/opt/java
```

### Step 3: Create Symlink
```bash
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java
```

You'll need to enter your Mac password when prompted.

### Step 4: Verify
```bash
which java           # Should show: /usr/local/bin/java
java -version       # Should show: openjdk version "17.x.x"
```

---

## ✅ After Java is Installed

Once Java is verified, proceed with Maven:

```bash
# Install Maven
brew install maven

# Verify Maven
mvn -version

# Go to project directory
cd "/Users/nithineleti/Downloads/PROJECTS/Hospital Database Management System"

# Build project
mvn clean install -DskipTests

# Run application
mvn spring-boot:run
```

---

## 🐛 Troubleshooting Java Installation

### Issue: "sudo: no tty present and askpass program specified"

**Solution:** Run without sudo redirect, or just use sudo before symlink:
```bash
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java
```

### Issue: "Permission denied" on symlink

**Solution:** Use sudo:
```bash
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java
```

### Issue: "java: command not found" after installation

**Solution:** Verify symlink was created:
```bash
# Check if symlink exists
ls -la /usr/local/bin/java

# If not, create it:
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java

# Add to PATH if needed (add to ~/.zshrc):
echo 'export PATH=/usr/local/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

### Issue: Multiple Java versions installed

**Solution:** Find which one is active:
```bash
which java              # Shows active java
java -version           # Shows version

# List all installed Java versions:
brew list | grep java

# Switch to specific version:
sudo ln -sfn $(brew --prefix)/opt/openjdk@17/bin/java /usr/local/bin/java
```

---

## 📚 Additional Resources

**Official OpenJDK:** https://openjdk.java.net/
**Homebrew Java:** https://brew.sh/
**Spring Boot Requirements:** https://spring.io/projects/spring-boot

---

## ✨ You're Ready Now!

After completing the steps above:

1. ✅ Java 17 installed
2. ✅ Symlink created
3. ✅ Run Maven build
4. ✅ Launch application

---

**Now proceed to:**
1. Install Maven: `brew install maven`
2. Build project: `mvn clean install -DskipTests`
3. Run project: `mvn spring-boot:run`
4. Open: `http://localhost:8080/hospital/auth/login`

Let me know if you need help with the next steps! 🚀
