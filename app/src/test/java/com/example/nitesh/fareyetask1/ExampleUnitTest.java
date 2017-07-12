package com.example.nitesh.fareyetask1;

import com.example.nitesh.fareyetask1.activities.MainActivity;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void fetchdata(){
        DataModel dataModel;
        String url="\"albumId,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"accusamus beatae ad facilis cum similique qui sunt\",\n" +
                "    \"url\": \"http://placehold.it/600/92c952\",\n" +
                "    \"thumbnailUrl\": \"http://placehold.it/150/92c952\"";
        MainActivity mainActivity=new MainActivity();
        mainActivity.fetchData(url);
        assertEquals(2+2,3);

    }
    @Test
    public void localdb(){

    }
}