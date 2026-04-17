# About `horaryo`

<img width="1000" height="750" alt="mandelamichael" src="https://github.com/user-attachments/assets/b12f5589-e263-42e8-96c8-a3988b34e623" />

# Creator
- Keith Ashly M. Domingo
- Christian Jave Hulleza
- EJ Mayor Tolentino
- John Clyde C. Aparicio

**Date: December 7, 2024**

# Description

`horaryo` is a slender-man like 2D pixel game set on a dormitory, where you have to navigate around and gather exam books while also avoiding the horrors that lurk on the halls of the dormitory. Make sure to collect all 5 exam books while managing your stamina and bettery, escape quickly before they catch up to you after getting all of them because they will surely stop you. This program was made as the CMSC 22 final project and any similarities and correlation to any person and building, has been asked for permission and have been accepted.

# Features

**`horaryo`** offers challenging mechanics that will surely test your skills:

**Exam Books**
- Accross the whole dormitory, exam books will be scattered around and you must collect all of them while avoiding the ghosts in the halls. There will be a total of 5 books with the first and last book spawning at your room.

**Crouch and Run**
- You can try to crouch to avoid flying ghost and also run around to get to locations faster or to avoid certain ghosts that will try to chase you.

**Flashlight**
- Your phone will be with you across this journey, it has no signal but it still has some battery. This will be your primary light source when things get too dark make sure to properly manage it though as it doesn't last permanently.
  
**Ghosts**
- There are a wide variety of ghosts that will stop you in endeavors, each of which will spawn after collecting a certain number of books:
  1. `Flying Ghost` spawns after collecting the first book, they are spawned across all rooms and can be dodged by properly crouching at the right moment.
  2. `Blind Ghost` spawns after collecting the second book, same with the flying ghost are spawned across all rooms, as the ghost is blind make sure to not run in his area cause he can still hear.
  3. `Running Ghost` randomly spawns after collecting the third book, they will spawn randomly and will chase the player but will dissapear after enough time or changing rooms.
  4. `Darkness Ghost` also randomly spawns after collecting the third book, they spawn randomly and will also chase the player but will only dissapear if you turn your flashlight to them or change room.
  5. `Nightmare Ghost` will finally spawn after collecting the fifth book that will spawn in your room after collecting the fourth book, they will chase the player randomly and if you get caught the game crashes.

**Dormitory**
- The setting is a school dormitory which offers a big area to explore with two floors and around ten areas, seven of which are hallways, one counter, one garden, and one being your room.  

# Game Mechanic

- **Stamina and Battery** The character has `stamina` and `battery`, stamina which is used when running can run out but will be regained after stopping or by walking around, while the battery doesn't regain its battery and will be gone when exhausted so the player must properly manage these resources to be able to escape.

- **Ghosts** All `Ghosts` at contact will catch the player and reset the game in your room with your progress saved, with the sole exception of the `Nightmare Ghost`, which at contact will immeditately terminate the game causing you to reset your progress doing everything again, so make sure to avoid these ghosts and escape.

- **Exam Books** The `Books` are spawned discretely and will wait for another book to be collected before they are spawned forcing you to always roam around the dormitory to locate them. There are five books in total, the first one will be spawned at your room giving you the chance to explore before starting the game, which will then after picking up start the process of spawning until you reach the fourth book causing all ghosts to dissapear and spawns the fifth book in your room, to start the final chase.

There might be more implementations and mechanics in future updates so stay tuned!

# Controls Documentation
As the player, you can interact with the game using the following commands:
- `arrow-keys` - to be able to move around.
- `shift` - to run and increase your speed.
- `c-key` - to be able to crouch and again to stand up.
- `e-key` - to interact and claim the books and enter doors.
- `f-key` - to be able to turn on the flashlight and off.

# Limitations and Issues
- **(Game) Last Book Spawning:** There will be cases that the last book is spawned twice, making it so that after picking the book it will still be there giving you the option of just picking up the book and causing the ending sequence to happen.
- **(Compilation) Java Dependencies:** As the game has been implemeted in java and hasn't been exported the user must have downloaded Java to be able to play the game.

# Graphics and Tools 
- Pixilart Studio: [Pixilart](https://www.pixilart.com/)
- Audacity: [Audacity](audacityteam.org)

# Credits

The sprited were done to by team and was inspired by friends and acquintances which approvals were received.\
Credits to the people who made the free sounds effects that came from the following websites:
- Pixabay Sound Effects: [Pixabay](https://pixabay.com/)

# Getting Started: Players
To properly try and experience **`horaryo`**, you may follow the given steps:
1. Go to the official [Java download page](https://www.java.com/en/download/manual.jsp) and select the **Windows Offline (64-bit)** installer.
2. Double-click the downloaded .exe file. If prompted by User Account Control, click **Yes**.
3. Click the **Install button** in the setup window. The installer will extract files and complete the process automatically.
4. **Locate** the folder of the Java file in your Program files and add the bin folder in the **Path** in your **System Environments**.
5. Open a **Command Prompt** and type java -version. It should display the installed version number.
```bash
java -version
```
6. Clone the repository:
```bash
git clone https://github.com/FakeThird/horaryo.git
```
7.  Run the following program while in the directory and then enjoy the game!
```bash
java main/HoraryoApplication
```

# License

- This game was created as a passionate machine problem for CMSC 22 - Fundamentals of Object-oriented Programming.
- **All Credits of Sounds and Inspiration goes to their respective owners.** 
- Sprites were custom built by the creators and is free for personal, non-commercial use.
