package com.expedia.interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImageStore
{
    private final Map<String, Integer> idToHashcode;
    private final Map<Integer, byte[]> hashcodeToImage;
    private final Map<Integer, Set<String>> hashcodeToIds;

    public ImageStore()
    {
        super();
        this.hashcodeToImage = new HashMap<>();
        this.idToHashcode = new HashMap<>();
        this.hashcodeToIds = new HashMap<>();
    }

    /**
     * Inserts an image in the store
     *
     * @param id
     *            -- The identifier of the image
     * @param content
     *            -- The content of the image
     */
    public void storeImage(final String id, final byte[] image)
    {
        // Hashcode of each image(byte array) - see below. Check if this
        // ID points to an image already
        Integer existingHashcodeForId = idToHashcode.get(id);

        if (existingHashcodeForId != null)
        {
            // Get all the ids that point to this image
            Set<String> setOfIdsForExistingHashCode = getIdsForHashCode(existingHashcodeForId);
            // Since we have a new image for this id, remove this id for the old one
            setOfIdsForExistingHashCode.remove(id);

            // And if no more ids point to the image, remove the image
            if (setOfIdsForExistingHashCode.size() == 0)
            {
                hashcodeToImage.remove(existingHashcodeForId);
                hashcodeToIds.remove(existingHashcodeForId);
            }
        }

        // get a hashcode of the byte array to use as a map key. That way we can delete an
        // image without looping through some other data structure everytime
        int hashCode = Arrays.hashCode(image);
        hashcodeToImage.put(hashCode, image);
        idToHashcode.put(id, hashCode);

        // A set of ids against each hashcode so we can check how many ids point to each
        // image (to know when to delete)
        Set<String> setOfIdsForNewHashCode = getIdsForHashCode(hashCode);
        setOfIdsForNewHashCode.add(id);
    }

    private Set<String> getIdsForHashCode(final int hashCode)
    {
        Set<String> set = hashcodeToIds.get(hashCode);

        if (set == null)
        {
            set = new HashSet<String>();
            hashcodeToIds.put(hashCode, set);
        }

        return set;
    }


    /**
     * Retrieves an image from the store
     *
     * @param id
     *            -- The identifier of the image to be retrieved
     * @return the image content
     */
    public byte[] fetchImage(final String id)
    {
        Integer hashcode = idToHashcode.get(id);

        if(hashcode!=null) {
            return hashcodeToImage.get(hashcode);
        }

        return null;
    }

    /**
     * The size of the store
     *
     * @return the actual store size
     */
    public int size()
    {
        return hashcodeToImage.size();
    }
}
