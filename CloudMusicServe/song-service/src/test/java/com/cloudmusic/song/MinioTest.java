package com.cloudmusic.song;

import com.cloudmusic.song.util.MinioUtil;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@SpringBootTest(classes = SongServiceApplication.class)
@RunWith(SpringRunner.class)
public class MinioTest {

    @Autowired
    MinioUtil minioUtil;

    @Autowired
    MinioClient minioClient;

    @Test
    public void test() {
        System.out.println(minioUtil.existBucket("cloudmusic"));
    }

    @Test
    public void delete() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("/images/我的头像_1653300017574.jpg");
        Iterable<Result<DeleteError>> results = minioUtil.removeObjects("cloudmusic", strings);
        for (Result<DeleteError> result : results) {
            try {
                DeleteError deleteError = result.get();
                System.out.println(deleteError);
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } catch (InsufficientDataException e) {
                e.printStackTrace();
            } catch (InternalException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InvalidResponseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (XmlParserException e) {
                e.printStackTrace();
            }
        }
    }
}
