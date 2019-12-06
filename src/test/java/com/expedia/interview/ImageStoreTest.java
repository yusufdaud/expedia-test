package com.expedia.interview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageStoreTest
{
    private static final String STORMY_HOTELS = "Stormy hotels";
    private static final String SUNSHINE_HOTELS = "Sunshine hotels";
    private static final String BEACHFRONT_HOTELS = "Beachfront hotels";

    private static byte[] imageA;
    private static byte[] imageB;

    ImageStore imageStore;

    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException
    {
        imageA = getRandomByteArray(10);
        imageB = getRandomByteArray(20);
    }

    @Before
    public void init()
    {
        imageStore = new ImageStore();
    }

    @Test
    public void testMapReturnsNullWhenImageDoesNotExist()
    {
        assertNull(imageStore.fetchImage("id"));
        assertThat(imageStore.size(), is(0));
    }


    @Test
    public void testStoreSingleImage() throws NoSuchAlgorithmException
    {
        imageStore.storeImage(SUNSHINE_HOTELS, imageA);
        byte[] fetchImage = imageStore.fetchImage(SUNSHINE_HOTELS);

        assertThat(fetchImage, is(imageA));
        assertThat(imageStore.size(), is(1));
    }

    @Test
    public void testStoreTwoImagesWithSameIdStoresOnlySecondImage() throws NoSuchAlgorithmException
    {
        imageStore.storeImage(SUNSHINE_HOTELS, imageA);
        imageStore.storeImage(SUNSHINE_HOTELS, imageB);

        byte[] fetchImage = imageStore.fetchImage(SUNSHINE_HOTELS);

        assertThat(fetchImage, not(imageA));
        assertThat(fetchImage, is(imageB));
        assertThat(imageStore.size(), is(1));
    }

    @Test
    public void testStoreSameImageWithTwoIdsStoresOneUnderBothIds() throws NoSuchAlgorithmException
    {
        imageStore.storeImage(SUNSHINE_HOTELS, imageA);
        imageStore.storeImage(STORMY_HOTELS, imageA);

        assertThat(imageStore.fetchImage(SUNSHINE_HOTELS), is(imageA));
        assertThat(imageStore.fetchImage(STORMY_HOTELS), is(imageA));
        assertThat(imageStore.size(), is(1));
    }

    @Test
    public void testStoreTwoImagesUnderThreeIds()
    {
        imageStore.storeImage(SUNSHINE_HOTELS, imageA);
        imageStore.storeImage(STORMY_HOTELS, imageA);
        imageStore.storeImage(BEACHFRONT_HOTELS, imageB);

        assertThat(imageStore.fetchImage(SUNSHINE_HOTELS), is(imageA));
        assertThat(imageStore.fetchImage(STORMY_HOTELS), is(imageA));
        assertThat(imageStore.fetchImage(BEACHFRONT_HOTELS), is(imageB));

        assertThat(imageStore.size(), is(2));
    }

    @Test
    public void testOverwritingImageStoreFirstByIdThenByImage()
    {
        imageStore.storeImage(SUNSHINE_HOTELS, imageA);
        imageStore.storeImage(STORMY_HOTELS, imageA);
        imageStore.storeImage(SUNSHINE_HOTELS, imageB);
        imageStore.storeImage(STORMY_HOTELS, imageB);

        assertThat(imageStore.fetchImage(SUNSHINE_HOTELS), is(imageB));
        assertThat(imageStore.fetchImage(STORMY_HOTELS), is(imageB));
        assertThat(imageStore.size(), is(1));
    }

    private static byte[] getRandomByteArray(final int size) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[size];
        SecureRandom.getInstanceStrong().nextBytes(bytes);
        return bytes;
    }
}
