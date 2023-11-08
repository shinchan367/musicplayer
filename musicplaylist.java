import java.util.Scanner;

class Song {
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return title + " - " + artist;
    }
}

class Node {
    public Song data;
    public Node next;
    public Node prev;

    public Node(Song data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class PlaylistManager {
    private Node head;
    private Node tail;
    private int size;
    private int currentIndex;

    public PlaylistManager() {
        head = null;
        tail = null;
        size = 0;
        currentIndex = -1;
    }

    public void addSong(Song song) {
        Node newNode = new Node(song);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        System.out.println(song.getTitle() + " - " + song.getArtist() + " added to the playlist.");
    }

    public void removeSong(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid index. Song not removed.");
            return;
        }

        Node current = getNodeAtIndex(index);

        if (current == head) {
            head = head.next;
        } else if (current == tail) {
            tail = tail.prev;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }

        size--;
        if (currentIndex >= index) {
            currentIndex--;
        }
        System.out.println("Song at index " + (index + 1) + " removed from the playlist.");
    }

    public void shufflePlaylist() {
        // Convert the linked list to an array for easy shuffling
        Song[] songArray = new Song[size];
        Node current = head;
        for (int i = 0; i < size; i++) {
            songArray[i] = current.data;
            current = current.next;
        }

        // Shuffle the array
        for (int i = size - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Song temp = songArray[i];
            songArray[i] = songArray[j];
            songArray[j] = temp;
        }

        // Reconstruct the linked list with shuffled songs
        head = null;
        tail = null;
        size = 0;
        currentIndex = -1;
        for (Song song : songArray) {
            addSong(song);
        }

        System.out.println("Playlist shuffled.");
    }

    public void displayPlaylist() {
        if (size == 0) {
            System.out.println("Playlist is empty.");
            return;
        }

        System.out.println("Current Playlist:");
        Node current = head;
        int index = 1;
        while (current != null) {
            System.out.println(index + ". " + current.data);
            current = current.next;
            index++;
        }
    }

    public void playForward() {
        if (size == 0) {
            System.out.println("Playlist is empty. No song to play forward.");
            return;
        }

        if (currentIndex < size - 1) {
            currentIndex++;
        } else {
            System.out.println("End of the playlist. No more songs to play forward.");
            currentIndex = size - 1;
            return;
        }

        System.out.println("Now playing: " + getNodeAtIndex(currentIndex).data);
    }

    public void playBackward() {
        if (size == 0) {
            System.out.println("Playlist is empty. No song to play backward.");
            return;
        }

        if (currentIndex > 0) {
            currentIndex--;
        } else {
            System.out.println("Start of the playlist. No more songs to play backward.");
            currentIndex = 0;
            return;
        }

        System.out.println("Now playing: " + getNodeAtIndex(currentIndex).data);
    }

    public void playFromSong(String songTitle, String artistName) {
        Node current = head;
        int index = 0;
        while (current != null) {
            Song song = current.data;
            if (song.getTitle().equalsIgnoreCase(songTitle) && song.getArtist().equalsIgnoreCase(artistName)) {
                currentIndex = index;
                System.out.println("Now playing: " + getNodeAtIndex(currentIndex).data);
                return;
            }
            current = current.next;
            index++;
        }
        System.out.println("Song not found in the playlist.");
    }

    private Node getNodeAtIndex(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlaylistManager playlistManager = new PlaylistManager();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Song");
            System.out.println("2. Remove Song");
            System.out.println("3. Shuffle Playlist");
            System.out.println("4. Display Playlist");
            System.out.println("5. Play Forward");
            System.out.println("6. Play Backward");
            System.out.println("7. Play From Song");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter song title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter artist name: ");
                    String artist = scanner.nextLine();
                    playlistManager.addSong(new Song(title, artist));
                    break;
                case 2:
                    System.out.print("Enter the index of the song to remove: ");
                    int index = scanner.nextInt();
                    playlistManager.removeSong(index - 1);
                    break;
                case 3:
                    playlistManager.shufflePlaylist();
                    break;
                case 4:
                    playlistManager.displayPlaylist();
                    break;
                case 5:
                    playlistManager.playForward();
                    break;
                case 6:
                    playlistManager.playBackward();
                    break;
                case 7:
                    System.out.print("Enter song title: ");
                    String songTitle = scanner.nextLine();
                    System.out.print("Enter artist name: ");
                    String artistName = scanner.nextLine();
                    playlistManager.playFromSong(songTitle, artistName);
                    break;
                case 8:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
       
