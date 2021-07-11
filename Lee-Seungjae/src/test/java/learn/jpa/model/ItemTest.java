package learn.jpa.model;

import learn.jpa.repository.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemTest {
    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @Transactional
    void 앨범테스트() throws Exception{
        Album album = albumRepository.save(Album.builder()
                                     .artist("artist")
                                     .build());

        album.setName("name");


        assertThat(album.getArtist()).isEqualTo("artist");
        albumRepository.flush();
    }
}