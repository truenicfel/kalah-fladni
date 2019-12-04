# kalah-fladni

## Download 

### 1. Git Installation (falls nicht vorhanden)

#### Linux

sudo apt install git

#### Windows

Keine Ahnung: https://git-scm.com/download/win

#### 2. Clone

git clone --recurse-submodules https://github.com/KWARC/Kalah-Framework.git

(--recurse-submodules ist wichtig damit das Kalah Framewrork runtergeladen wird)


## Netbeans

In Netbeans dann einfach links oben New Project --> Java with Ant --> Java Project with existing Sources --> Namen eingeben, Ort wählen (egal) --> Dann bei "Source Package Folders" auf "Add Folder" klicken und das im zuvor geclonten git Repository den src Ordner auswählen "kalah-fladni/src" --> Finish drücken. 

Wenn ihr das Projekt habt einfach links im Projekt explorer öffnen dann gibt es ein unterordner mit dem Namen "Libraries". Rechtsklick --> "Add Jar/Folder..." --> Dann in den "Kalah-Framework/target/scala-2.12/" navigieren und "kalah_2.12-1.0.jar" auswählen. Ok drücken. Jetzt müsste die .jar Datei unterhalb des "Libraries" Verzeichnis auftauchen.
