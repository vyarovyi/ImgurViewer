package com.imgur.viewer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imgur.viewer.repositories.database.model.FeedItem;
import com.imgur.viewer.repositories.database.model.ItemTypeDescriptor;
import com.imgur.viewer.repositories.network.api.CustomJsonDeserializer;
import com.imgur.viewer.repositories.network.api.NetworkInterface;
import com.imgur.viewer.repositories.network.api.NetworkService;
import com.imgur.viewer.repositories.network.models.FeedResponse;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTest {


    @Test
    public void test01_fake() throws InterruptedException {
        final NetworkInterface ni = Mockito.mock(NetworkInterface.class);
        final Call<FeedResponse> mockedCall = Mockito.mock(Call.class);
        Mockito.when(ni.getFeed(0)).thenReturn(mockedCall);

        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Callback<FeedResponse> callback = invocation.getArgument(0);

                final String strResp = "{\n" +
                        "    \"data\": [\n" +
                        "       {\n" +
                        "            \"id\": \"EgwV7VC\",\n" +
                        "            \"title\": \"What's one more body amongst foundations?\",\n" +
                        "            \"description\": null,\n" +
                        "            \"datetime\": 1559978780,\n" +
                        "            \"cover\": \"QhFCPQR\",\n" +
                        "            \"cover_width\": 1956,\n" +
                        "            \"cover_height\": 2048,\n" +
                        "            \"account_url\": \"lordofshapes\",\n" +
                        "            \"account_id\": 30635457,\n" +
                        "            \"privacy\": \"public\",\n" +
                        "            \"layout\": \"blog\",\n" +
                        "            \"views\": 24370,\n" +
                        "            \"link\": \"https://imgur.com/a/EgwV7VC\",\n" +
                        "            \"ups\": 726,\n" +
                        "            \"downs\": 13,\n" +
                        "            \"points\": 713,\n" +
                        "            \"score\": 725,\n" +
                        "            \"is_album\": true,\n" +
                        "            \"vote\": null,\n" +
                        "            \"favorite\": false,\n" +
                        "            \"nsfw\": false,\n" +
                        "            \"section\": \"\",\n" +
                        "            \"comment_count\": 22,\n" +
                        "            \"favorite_count\": 155,\n" +
                        "            \"topic\": \"No Topic\",\n" +
                        "            \"topic_id\": 29,\n" +
                        "            \"images_count\": 1,\n" +
                        "            \"in_gallery\": true,\n" +
                        "            \"is_ad\": false,\n" +
                        "            \"tags\": [\n" +
                        "                {\n" +
                        "                    \"name\": \"cat\",\n" +
                        "                    \"display_name\": \"cat\",\n" +
                        "                    \"followers\": 716116,\n" +
                        "                    \"total_items\": 222398,\n" +
                        "                    \"following\": false,\n" +
                        "                    \"background_hash\": \"xeEIpAn\",\n" +
                        "                    \"thumbnail_hash\": null,\n" +
                        "                    \"accent\": \"159559\",\n" +
                        "                    \"background_is_animated\": false,\n" +
                        "                    \"thumbnail_is_animated\": false,\n" +
                        "                    \"is_promoted\": false,\n" +
                        "                    \"description\": \"feline friends\",\n" +
                        "                    \"logo_hash\": null,\n" +
                        "                    \"logo_destination_url\": null,\n" +
                        "                    \"description_annotations\": {}\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"name\": \"comic\",\n" +
                        "                    \"display_name\": \"comic\",\n" +
                        "                    \"followers\": 48846,\n" +
                        "                    \"total_items\": 57383,\n" +
                        "                    \"following\": false,\n" +
                        "                    \"background_hash\": \"1fDftsZ\",\n" +
                        "                    \"thumbnail_hash\": null,\n" +
                        "                    \"accent\": \"027B89\",\n" +
                        "                    \"background_is_animated\": false,\n" +
                        "                    \"thumbnail_is_animated\": false,\n" +
                        "                    \"is_promoted\": false,\n" +
                        "                    \"description\": \"Heroes on paper\",\n" +
                        "                    \"logo_hash\": null,\n" +
                        "                    \"logo_destination_url\": null,\n" +
                        "                    \"description_annotations\": {}\n" +
                        "                },\n" +
                        "                {\n" +
                        "                    \"name\": \"webcomic\",\n" +
                        "                    \"display_name\": \"webcomic\",\n" +
                        "                    \"followers\": 11271,\n" +
                        "                    \"total_items\": 3259,\n" +
                        "                    \"following\": false,\n" +
                        "                    \"background_hash\": \"f8B0kEw\",\n" +
                        "                    \"thumbnail_hash\": null,\n" +
                        "                    \"accent\": \"5B62A5\",\n" +
                        "                    \"background_is_animated\": false,\n" +
                        "                    \"thumbnail_is_animated\": false,\n" +
                        "                    \"is_promoted\": false,\n" +
                        "                    \"description\": \"\",\n" +
                        "                    \"logo_hash\": null,\n" +
                        "                    \"logo_destination_url\": null,\n" +
                        "                    \"description_annotations\": {}\n" +
                        "                }\n" +
                        "            ],\n" +
                        "            \"ad_type\": 0,\n" +
                        "            \"ad_url\": \"\",\n" +
                        "            \"in_most_viral\": true,\n" +
                        "            \"include_album_ads\": false,\n" +
                        "            \"images\": [\n" +
                        "                {\n" +
                        "                    \"id\": \"QhFCPQR\",\n" +
                        "                    \"title\": null,\n" +
                        "                    \"description\": null,\n" +
                        "                    \"datetime\": 1559978730,\n" +
                        "                    \"type\": \"image/png\",\n" +
                        "                    \"animated\": false,\n" +
                        "                    \"width\": 1956,\n" +
                        "                    \"height\": 2048,\n" +
                        "                    \"size\": 2231714,\n" +
                        "                    \"views\": 22119,\n" +
                        "                    \"bandwidth\": 49363281966,\n" +
                        "                    \"vote\": null,\n" +
                        "                    \"favorite\": false,\n" +
                        "                    \"nsfw\": null,\n" +
                        "                    \"section\": null,\n" +
                        "                    \"account_url\": null,\n" +
                        "                    \"account_id\": null,\n" +
                        "                    \"is_ad\": false,\n" +
                        "                    \"in_most_viral\": false,\n" +
                        "                    \"has_sound\": false,\n" +
                        "                    \"tags\": [],\n" +
                        "                    \"ad_type\": 0,\n" +
                        "                    \"ad_url\": \"\",\n" +
                        "                    \"edited\": \"0\",\n" +
                        "                    \"in_gallery\": false,\n" +
                        "                    \"link\": \"https://i.imgur.com/QhFCPQR.png\",\n" +
                        "                    \"comment_count\": null,\n" +
                        "                    \"favorite_count\": null,\n" +
                        "                    \"ups\": null,\n" +
                        "                    \"downs\": null,\n" +
                        "                    \"points\": null,\n" +
                        "                    \"score\": null\n" +
                        "                }\n" +
                        "            ],\n" +
                        "            \"ad_config\": {\n" +
                        "                \"safeFlags\": [\n" +
                        "                    \"album\",\n" +
                        "                    \"in_gallery\",\n" +
                        "                    \"onsfw_mod_safe\",\n" +
                        "                    \"gallery\"\n" +
                        "                ],\n" +
                        "                \"highRiskFlags\": [],\n" +
                        "                \"unsafeFlags\": [],\n" +
                        "                \"showsAds\": true\n" +
                        "            }\n" +
                        "        },\n" +
                        "{\n" +
                        "            \"id\": \"r0yF6br\",\n" +
                        "            \"title\": \"It's not KPOP, Russian GTA, a selfie, a shameless instagram whoreing or any other soam-bot. So Fuck ya\",\n" +
                        "            \"description\": null,\n" +
                        "            \"datetime\": 1559978576,\n" +
                        "            \"type\": \"image/jpeg\",\n" +
                        "            \"animated\": false,\n" +
                        "            \"width\": 640,\n" +
                        "            \"height\": 882,\n" +
                        "            \"size\": 157918,\n" +
                        "            \"views\": 26025,\n" +
                        "            \"bandwidth\": 4109815950,\n" +
                        "            \"vote\": null,\n" +
                        "            \"favorite\": false,\n" +
                        "            \"nsfw\": false,\n" +
                        "            \"section\": \"\",\n" +
                        "            \"account_url\": \"TheOnlyCanadianWhoDoesNotLikeHockey\",\n" +
                        "            \"account_id\": 10926748,\n" +
                        "            \"is_ad\": false,\n" +
                        "            \"in_most_viral\": true,\n" +
                        "            \"has_sound\": false,\n" +
                        "            \"tags\": [],\n" +
                        "            \"ad_type\": 0,\n" +
                        "            \"ad_url\": \"\",\n" +
                        "            \"edited\": 0,\n" +
                        "            \"in_gallery\": true,\n" +
                        "            \"topic\": \"No Topic\",\n" +
                        "            \"topic_id\": 29,\n" +
                        "            \"link\": \"https://i.imgur.com/r0yF6br.jpg\",\n" +
                        "            \"ad_config\": {\n" +
                        "                \"safeFlags\": [\n" +
                        "                    \"in_gallery\",\n" +
                        "                    \"onsfw_mod_safe\",\n" +
                        "                    \"gallery\"\n" +
                        "                ],\n" +
                        "                \"highRiskFlags\": [],\n" +
                        "                \"unsafeFlags\": [],\n" +
                        "                \"showsAds\": true\n" +
                        "            },\n" +
                        "            \"comment_count\": 33,\n" +
                        "            \"favorite_count\": 92,\n" +
                        "            \"ups\": 760,\n" +
                        "            \"downs\": 38,\n" +
                        "            \"points\": 722,\n" +
                        "            \"score\": 734,\n" +
                        "            \"is_album\": false\n" +
                        "        }\n" +
                        " ],\n" +
                        "    \"success\": true,\n" +
                        "    \"status\": 200\n" +
                        "}";


                FeedResponse feedResponse = new GsonBuilder()
                        .registerTypeAdapter(FeedResponse.class, new CustomJsonDeserializer())
                        .create().fromJson(strResp, FeedResponse.class);

                callback.onResponse(mockedCall, Response.success(feedResponse));
                return null;
            }
        }).when(mockedCall).enqueue(any(Callback.class));

        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<FeedResponse> resp = new AtomicReference<>();
        ni.getFeed(0).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                resp.set(response.body());
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await(10, TimeUnit.SECONDS);

        Assert.assertNotNull(resp.get());
        Assert.assertEquals(resp.get().getItems().size(), 2);
        FeedItem item = resp.get().getItems().get(0);
        Assert.assertNotNull(item);
        Assert.assertNotEquals(item.getType(), ItemTypeDescriptor.TYPE_IMAGE_JPG);
    }

    @Test
    public void test02_real() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<FeedResponse> resp = new AtomicReference<>();
        NetworkInterface ni = NetworkService.getInstance().getClient();
        ni.getFeed(0).enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                resp.set(response.body());
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(10, TimeUnit.SECONDS);
        Assert.assertEquals(resp.get().getStatus(), 200);
        Assert.assertNotNull(resp.get());
        Assert.assertNotNull(resp.get().getItems());
        Assert.assertNotEquals(resp.get().getItems().size(), 0);
    }
}
