public class TestHash {
    public static void main(String[] args) throws Exception {
        String password = "admin123";  // mật khẩu bạn muốn dùng
        String salt = "prj@301#2025";
        int iterations = 65536;
        int keyLength = 256;

        byte[] saltBytes = salt.getBytes("UTF-8");
        javax.crypto.spec.PBEKeySpec spec =
            new javax.crypto.spec.PBEKeySpec(password.toCharArray(), saltBytes, iterations, keyLength);
        javax.crypto.SecretKeyFactory skf =
            javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();

        String hashed = java.util.Base64.getEncoder().encodeToString(hash);
        System.out.println("Hash PBKDF2: " + hashed);
    }
}
