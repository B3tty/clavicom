rem Cr�ation de l'installeur en JAR
cd "Jar installer"
"IzPack\bin\compile" "Install instruction.xml" -o "..\Temp\ClavicomNG-install.jar" -h "IzPack" -b "..\..";
cd..