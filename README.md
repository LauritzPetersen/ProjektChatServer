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

## Initial Contact:
<img width="432" height="362" alt="image" src="https://github.com/user-attachments/assets/65be3c65-bfd3-45ec-a46e-b4b105960915" />
<img width="867" height="617" alt="image" src="https://github.com/user-attachments/assets/ca4db3fe-e19a-4ce4-a5b4-d1913db48497" />
<img width="837" height="455" alt="image" src="https://github.com/user-attachments/assets/bbebc625-4bcf-41a2-9f7b-433f7188e636" />

### Vi ville sikre os at forbindelsen til serveren ikke lukkede hvis et brugernavn var optaget og gav derfor nogen ekstra tydlige instrukser for at sikre at fejlhåndtering håndteres korrekt.

<img width="892" height="637" alt="image" src="https://github.com/user-attachments/assets/34e77c3e-ed9c-422c-a830-050a7507f6b4" />
<img width="910" height="672" alt="image" src="https://github.com/user-attachments/assets/6490a03a-7621-4e2e-9991-589ce5b77b17" />
<img width="865" height="156" alt="image" src="https://github.com/user-attachments/assets/4857477d-0791-4126-bf03-871baea2c69e" />

### Første implementeringsplan havde nogle mangler som vi kontrollede ved at gennemlæse kodeændringerne, derfor beder vi agenten om en opdateret implementeringsplan. 

<img width="916" height="432" alt="image" src="https://github.com/user-attachments/assets/837fe627-24ea-4c64-bb7f-515ba70c4281" />
<img width="901" height="347" alt="image" src="https://github.com/user-attachments/assets/135914e4-fd2e-4919-86ce-e1806ed9ee53" />
<img width="837" height="655" alt="image" src="https://github.com/user-attachments/assets/e55e79f2-22a6-448e-93af-39d43bd91bdd" />

### Vi kontrollerer nu den nye plan og godkender den.

<img width="891" height="526" alt="image" src="https://github.com/user-attachments/assets/b08ed1b3-b50e-4bf3-a931-9e318d4312df" />
<img width="851" height="582" alt="image" src="https://github.com/user-attachments/assets/c0808016-9d43-495a-bdcf-940bdfcf8649" />
<img width="862" height="422" alt="image" src="https://github.com/user-attachments/assets/40c5c08a-0d9b-49d5-ace0-ac3c7ee0bb5f" />

### Vi gennemtester den nye kode ved at køre programmet og kontrollere om systemet virker som det skal i forhold til oprettelse af bruger. Det virker ikke som det skal, men vi har problemer med at finde den konkrete fejl, og prompter derfor agenten igen og med de konkrete fejl vi har fundet, og beder den arbejde på en løsning samt finde grunden til at programmet ikke virker. 

<img width="895" height="456" alt="image" src="https://github.com/user-attachments/assets/b4b6325d-5efa-41ea-bb34-d80b7f5b242f" />
<img width="902" height="347" alt="image" src="https://github.com/user-attachments/assets/dadde409-72e0-4a99-addf-34e0e6bf2836" />
<img width="887" height="442" alt="image" src="https://github.com/user-attachments/assets/51d63711-71ad-46fd-9407-6c2758ee0c36" />

### Agenten kommer nu med en løsning der opfylder vores krav og som virker, samt kommer med grundlag for hvorfor den tidligere iteration af systemet ikke virkede. Til slut beder vi agenten oprette en pull request. 




# Valgt udvidelse

### Vi endte ikke med at have nået en udvidelse, da vi fokuserede på at få kernen til at fungere.




