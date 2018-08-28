package com.cdkj.fastdfs.pool;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;
import java.io.InputStream;

public class FastdfsClient extends StorageClient1 {

    private int active;

    private final int objMaxActive;


    public FastdfsClient(TrackerServer trackerServer, StorageServer storageServer, int objMaxActive) {
        super(trackerServer, storageServer);
        this.objMaxActive = objMaxActive;
    }


    public TrackerServer getTrackerServer() {
        return this.trackerServer;
    }

    public StorageServer getStorageServer() {
        return this.storageServer;
    }

    public void tryReset() throws IOException {
        active++;
        if (active <= objMaxActive) {
            return;
        }
        reset();
    }


    /**
     * tip：重新获取storageServer trackerServer
     */
    public void reset() throws IOException {

        if (this.trackerServer != null) {
            try {
                this.trackerServer.close();
            } catch (IOException e) {
                throw e;
            } finally {
                this.trackerServer = null;
            }
        }
        if (storageServer != null) {
            try {
                storageServer.close();
            } catch (IOException e) {
                throw e;
            } finally {
                this.storageServer = null;
            }
        }

    }

    /**
     * tip：重新获取storage
     */
    public void reset0() {
        if (storageServer != null) {
            try {
                storageServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.storageServer = null;
            }
        }
    }

    /**
     * @param group_name    can be empty
     * @param file_name
     * @param file_ext_name can be empty
     * @param inputStream
     * @param file_size
     * @param meta_list
     * @return
     */
    public String upload_file(String group_name, String file_name, String file_ext_name, InputStream inputStream, Long file_size, NameValuePair[] meta_list) throws IOException, MyException {
        final byte cmd = ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE;
        if (file_ext_name == null) {
            int nPos = file_name.lastIndexOf('.');
            if (nPos > 0 && file_name.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
                file_ext_name = file_name.substring(nPos + 1);
            }
        }

        try {
            String[] parts = this.do_upload_file(cmd, group_name, null, null, file_ext_name, file_size,
                    new UploadStream(inputStream, file_size), meta_list);
            if (parts != null) {
                return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
            }

        } catch (IOException | MyException e) {
            throw e;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
