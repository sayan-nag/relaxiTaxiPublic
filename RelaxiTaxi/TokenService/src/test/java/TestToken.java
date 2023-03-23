import com.relaxiTaxi.token.security.JWTTokenUtils;
import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Random;

public class TestToken {

    @Test
    public void testToken() throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        String token = JWTTokenUtils.createToken();
        Assert.assertTrue(JWTTokenUtils.validateToken(token));
    }

    @Test
    public void testOTP(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        System.out.println(String.format("%06d", number));
    }
}
