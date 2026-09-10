# Forslag til klassestruktur 

### I kan tage udgangspunkt i følgende klasser: 
#### * ChatServer starter serveren og accepterer forbindelser.  
#### * ClientHandler håndterer kommunikationen med én klient.  
#### * ChatClient forbinder klienten og sender brugerens beskeder.  
#### * ServerListener modtager beskeder fra serveren.  
#### * Message repræsenterer en besked.  
#### * MessageParser opbygger og parser protokolbeskeder.  
#### * ClientRegistry holder styr på tilsluttede brugere. 
#### * ChatRoomManager holder styr på chatrum og medlemmer.  

# Arbejdsproces 

### Udvikl programmet i mindre trin: 
#### Få én klient til at forbinde og sende én besked.  
#### Tilføj ExecutorService, og forbind mindst tre klienter.  
#### Tilføj unikke brugernavne og broadcast.  
#### Tilføj chatrum og private beskeder.  
#### Tilføj fejlhåndtering og korrekt afbrydelse.  
#### Gennemfør de obligatoriske tests.  
#### Implementer den valgte udvidelse.  
