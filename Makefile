# Makefile 

# Directories
SRC_DIR := src/main/java
TEST_DIR := test/java
BIN_DIR := bin
JUNIT_JAR := lib/junit-platform-console-standalone-1.10.2.jar

# Main classes to compile
MAIN_CLASSES := $(shell find $(SRC_DIR) -name "*.java")
TEST_CLASSES := $(shell find $(TEST_DIR) -name "*.java")

# Command javac avec classpath
JAVAC := javac -d $(BIN_DIR)

# Targets

all: compile

# Command javac with classpath
compile:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) $(MAIN_CLASSES)

# Simulation execution
run: compile
	java -cp $(BIN_DIR) simulation.Simulation

# Compiling and running tests
test: compile
	$(JAVAC) -cp "$(BIN_DIR):$(JUNIT_JAR)" $(TEST_CLASSES)
	java -jar $(JUNIT_JAR) --class-path $(BIN_DIR) --scan-class-path

# Cleanup of compiled files
clean:
	rm -rf $(BIN_DIR)
