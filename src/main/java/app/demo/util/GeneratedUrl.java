package app.demo.util;

public class GeneratedUrl {
    
    public static String generateUrl(String publicId) {

        if (publicId.startsWith("http")) {
            return publicId;
        }

        return "https://res.cloudinary.com/dldsnde02n/image/upload/v1775063944/" + publicId;
    }


}
