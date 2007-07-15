rem Conversion du JAR de l'application (pas l'installeur) en executable
cd "Executable"
"launch4j\launch4jc.exe" "..\Config.xml"
cd..