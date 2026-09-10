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
