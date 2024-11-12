import org.example.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAss {
    private Main store;

    @BeforeEach
    public void setUp() {
        store = new Main(3);  // Initialize with capacity of 3
    }

    @Test
    public void testAddSongsWithinCapacity() {
        store.playSong("user1", "S1");
        store.playSong("user1", "S2");
        store.playSong("user1", "S3");

        List<String> expectedSongs = Arrays.asList("S1", "S2", "S3");
        assertEquals(expectedSongs, store.getRecentSongs("user1"));
    }

    @Test
    public void testAddSongsBeyondCapacity() {
        store.playSong("user1", "S1");
        store.playSong("user1", "S2");
        store.playSong("user1", "S3");
        store.playSong("user1", "S4");  // This should push out "S1"

        List<String> expectedSongs = Arrays.asList("S2", "S3", "S4");
        assertEquals(expectedSongs, store.getRecentSongs("user1"));
    }

    @Test
    public void testReplayingSongMovesToRecentEnd() {
        store.playSong("user1", "S1");
        store.playSong("user1", "S2");
        store.playSong("user1", "S3");
        store.playSong("user1", "S2");  // Replay "S2" should move it to the end

        List<String> expectedSongs = Arrays.asList("S1", "S3", "S2");
        assertEquals(expectedSongs, store.getRecentSongs("user1"));
    }

    @Test
    public void testAddNewSongAfterReplayingExistingSongs() {
        store.playSong("user1", "S1");
        store.playSong("user1", "S2");
        store.playSong("user1", "S3");
        store.playSong("user1", "S2");  // Replay "S2" should move it to the end
        store.playSong("user1", "S4");  // Adding "S4" should remove "S1"

        List<String> expectedSongs = Arrays.asList("S3", "S2", "S4");
        assertEquals(expectedSongs, store.getRecentSongs("user1"));
    }

    @Test
    public void testEmptyRecentSongsForNewUser() {
        List<String> expectedSongs = Arrays.asList();  // Expect empty list
        assertEquals(expectedSongs, store.getRecentSongs("newUser"));
    }

    @Test
    public void testMultipleUsersIndependence() {
        store.playSong("user1", "S1");
        store.playSong("user1", "S2");
        store.playSong("user2", "S3");  // Different user

        List<String> expectedSongsUser1 = Arrays.asList("S1", "S2");
        List<String> expectedSongsUser2 = Arrays.asList("S3");

        assertEquals(expectedSongsUser1, store.getRecentSongs("user1"));
        assertEquals(expectedSongsUser2, store.getRecentSongs("user2"));
    }
}