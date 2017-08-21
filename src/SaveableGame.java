import java.io.IOException;
import java.nio.file.Path;

public interface SaveableGame<ImmutableBoard> {
    void save(ImmutableBoard board, String name) throws IOException;
    void save(ImmutableBoard board, Path path) throws IOException;
    ImmutableBoard load(String name) throws IOException;
    ImmutableBoard load(Path path) throws IOException;
}
