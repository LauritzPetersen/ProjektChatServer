# Mini Chat Server
Dette er en programmeringsopgave. 

# Teknologi
* Brug Java 26.
* Brug ikke database.
* Brug ikke Spring Boot eller andre frameworks.
* Hold løsningen enkel og forståelig.

# GitHub workflow
Når du arbejder med en udviklingsopgave:
1. Brug et rigtigt GitHub Issue som kilde til opgaven.
2. Hent GitHub Issue gennem GitHub MCP.
3. Brug ikke README eller lokale markdown-filer som erstatning for et GitHub Issue.
4. Arbejd kun med Issues, der findes i GitHub Project.
5. Arbejd kun med det Issue, brugeren har valgt eller som er assigned til brugeren i Sprint Backlog.
6. Læs hele Issue og alle acceptkriterier før du planlægger.
7. Fortæl altid Issue-nummer og titel, før du foreslår en plan.
8. Lav en kort implementeringsplan før kode ændres.
9. Vent på brugerens godkendelse af planen.
10. Flyt Issue til In progress, når implementeringen starter.
11. Opret en separat branch til Issue.
12. Implementer kun det valgte Issue.
13. Kør relevante test efter implementering.
14. Kontroller alle acceptkriterier.
15. Referer Issue-nummeret i pull requesten.
16. Flyt Issue til Review, når pull requesten er klar.
17. Merge ikke uden menneskelig godkendelse.
18. Flyt ikke selv et Issue til Done uden menneskelig godkendelse.

# Kontekst
* Architectur skal være Clean Architectur.
* Der skal bruges Try-With-Resources.
* Vores færdige produkt skal være en ChatServer med flere forskellige chatrooms som ChatClients kan skrive og interegere med. Disse beskeder er underlagt protokol.
* Vi har en ChatServer klasse som starter serveren samt skal accepterer forbindelser fra instanser af ChatClient.
* En ClientHandler skal gennem ExecutorService forbinde en ChatClient til en ClientHandler. Der er 10 max clients, altså 10 ClientHandlers i form af en threadpool.
* ChatClient klassen repræsenterer den enkelte bruger som tilknytter sig ChatServeren og sender brugerens beskeder gennem terminalen.
* Hver ChatClient har et instans af ServerListener i et separat thread, som har til opgave at modtage beskeder fra serveren og udskrive dem i Klientens konsol.
* En statisk klasse MessageParser skal stå for at modtage tekst-strenge og dele dem op efter protokollen og oprette nye objekter af Message klassen som sendes tilbage
* En statisk klasse ClientRegistry skal holde styr på tilsluttede brugere f.eks. i forhold til at holde styr på at alle brugere har unikke brugernavne
* En statisk klasse ChatRoomManager skal holde styr på de forskellige chatrum og deres medlemmer, i forhold til f.eks. at holde styr på hvor beskeder fra brugerne broadcastes

# Kvalitet
* AI-genereret kode er ikke automatisk korrekt.
* Koden skal kunne forklares, testes og reviewes af udvikleren.
