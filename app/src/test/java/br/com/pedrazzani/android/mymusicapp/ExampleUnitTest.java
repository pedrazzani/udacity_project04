package br.com.pedrazzani.android.mymusicapp;

import org.junit.Test;

import br.com.pedrazzani.android.mymusicapp.entidades.Album;
import br.com.pedrazzani.android.mymusicapp.services.LastFmService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Album a = new LastFmService().searchAlbum("Rock or Bust","AC/DC");

        System.out.println(a.getArtista());
        System.out.println(a.getNome());
        System.out.println(a.getUrlImageSmall());
        System.out.println(a.getUrlImageMedium());
        System.out.println(a.getUrlImageLarge());
    }
}