package com.cloudmusic.user.delayedTask;

import com.cloudmusic.user.util.MinioUtil;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Component
public class DeleteNoUseImage {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private MinioUtil minioUtil;

    private final List<String> noUseImageList = new ArrayList<>();

    @Scheduled(cron = "59 59 23 * * *")
    public void deleteImage() {
        if (this.noUseImageList.size() != 0) {
            //minio批量删除是延时删除，需要调用返回值中的方法才能删除
            Iterable<Result<DeleteError>> results = minioUtil.removeObjects(bucketName, noUseImageList);
            for (Result<DeleteError> result : results) {
                try {
                    DeleteError deleteError = result.get();
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

    public void addItem(String noUseImage) {
        this.noUseImageList.add(noUseImage);
        System.out.println(this.noUseImageList);
    }

    public void deleteItem(String noUseImage) {
        this.noUseImageList.remove(noUseImage);
        System.out.println(this.noUseImageList);
    }
}
