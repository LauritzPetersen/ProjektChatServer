# Opstart af program

### Serveren startes ved main metoden i "ChatServer"-klassen.
### Klienter startes ved main metoden i "ChatClient"-klassen.


# Protokel-Procedure 

### Ved opstart som klient bedes man først indskrive et unikt brugernavn, dette kræver intet bestemt format.
### Efter man er "logget ind" bruger man formatet TYPE|TARGET|PAYLOAD til kommando kald. Ikke alle typer af kommandoer kræver alle tre dele, men skal altid bruge de to skille-linjer " | ".


# Klassediagram

<img width="1041" height="787" alt="billede" src="https://github.com/user-attachments/assets/069b39ce-9179-47a3-a9bc-2419527c6f1c" />



# Trådmodel?





# Delte Resourcer

### Vi bruger "ConcurrentHashMap"/"ConcurrentMap" i "ClientRegistry"-klassen og "ChatRoomManager"-klassen til at holde styr på delte lister. Disse gør brug af brugernavne som key og klientens instans af "ClientHandler" som value.

### Vi bruger "AtomicInteger" i "ChatServer"-klassen som bruges til at holde styr på antallet af forbundede klienter.

### I "ClientHandler"-klassen er et objekt af "MessageParser"-klassen sat som et static felt, som alle "ClientHandler"-instanser dermed deler.



# Testresultater (og unit-test)

### Vi har udført de specificerede test scenarier på nær test af udvidelse (læs mere nedenfor).
### Derudover har vi implementeret helt basale test af vores "MessageParser"-klasse som bruges til validering af brugerinput i form af kommandoer. Disse test er f.eks. i forhold til tomme input, mangel på skillelinjer " | ", samt korrekte beskedtyper f.eks. "PRIVATE".

### Vi har ikke haft midlerne/resourcerne til at teste med router, da vi ikke har haft adgang til sådanne apparater. Vi har derfor kun testet med brug af Lan-IP-adresse.


# AI-dokumentation


| Opgave | AI-værktøj |  AI's forslag | Vores vurdering og ændringer | Kontrol og test |
| --- | --- | --- | --- | --- |
| Planlægning af generelle trin | Gemini som planlægningsværktøj | Her er en detaljeret implementeringsplan bygget oven på jeres egne 7 trin, hvor vi inkorporerer jeres klasser, trådhåndtering og protokol... læs mere her https://gemini.google.com/share/7014a30133f7?skid=faed004c-6ab9-4957-a9cf-18042e4cce2d | Vi vurderede hvert trin og inkorporerede dem som issues, med lidt finpudsning samt tilføjede nogle ekstra trin selv | Som kontrol evaluerede baseret på egen viden og evner samt løbende som del af udviklingen | 



# Valgt udvidelse

### Vi endte ikke med at have nået en udvidelse, da vi fokuserede på at få kernen til at fungere.




