package org.example;
import java.util.*;

public class Main {

        private final int capacity;
        private final Map<String, LinkedList<String>> userSongs;

        public Main(int capacity) {
            this.capacity = capacity;
            this.userSongs = new HashMap<>();
        }

        public void playSong(String user, String song) {
            // Get or create the song list for the user
            userSongs.putIfAbsent(user, new LinkedList<>());
            LinkedList<String> songs = userSongs.get(user);

            // If the song is already in the list, remove it first (to re-add it at the end)
            songs.remove(song);

            // Add the song to the end of the list
            songs.add(song);

            // If the list exceeds capacity, remove the oldest song (from the start)
            if (songs.size() > capacity) {
                songs.removeFirst();
            }
        }

        public List<String> getRecentSongs(String user) {
            // Return a copy of the user's song list, or an empty list if the user has no songs
            return userSongs.getOrDefault(user, new LinkedList<>());
        }

        public static void main(String[] args) {
            Main store = new Main(3);  // Set capacity to 3

            // Simulate song plays
            store.playSong("user1", "S1");
            store.playSong("user1", "S2");
            store.playSong("user1", "S3");
            System.out.println(store.getRecentSongs("user1"));  // Output: [S1, S2, S3]

            store.playSong("user1", "S4");
            System.out.println(store.getRecentSongs("user1"));  // Output: [S2, S3, S4]

            store.playSong("user1", "S2");
            System.out.println(store.getRecentSongs("user1"));  // Output: [S3, S4, S2]

            store.playSong("user1", "S1");
            System.out.println(store.getRecentSongs("user1"));  // Output: [S4, S2, S1]
        }
}