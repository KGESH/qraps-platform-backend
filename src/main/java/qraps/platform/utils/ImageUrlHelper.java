package qraps.platform.utils;

public class ImageUrlHelper {

    private static final String BASE_URL = "/pdrpic/";
    private static final String FILE_EXTENSION = ".jpg";


    public final String getImageUrl(final String fileName) {
        if (fileName == null) {
            /** 빈 파일일때 X 박스 안보여주기 위해
             *  인코딩된 1x1 공백 이미지를 보여줌 */
            return "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=";
        }

        return BASE_URL + fileName + FILE_EXTENSION;
    }
}
