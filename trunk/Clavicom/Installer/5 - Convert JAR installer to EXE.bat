rem (4) Création de l'installeur en exe
cd "Executable installer"
"7-Zip\7z" a -t7z "..\Temp\ClavicomNG.7z" @"listfile.txt"
copy /B 7zS.sfx + config.txt + "..\Temp\ClavicomNG.7z" "..\ClavicomNG-install.exe"
cd..