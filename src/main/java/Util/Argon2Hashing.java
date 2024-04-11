package Util;

import java.security.SecureRandom;
import java.util.Base64;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;




public class Argon2Hashing {
	
	
	 public static String hashPassword(String password) {
	      
	        // Set realistic values for Argon2 parameters
	        int parallelism = 2; // Use 2 threads
	        int memory = 105536; // Use 64 MB of memory
	        int iterations = 5; // Run 3 iterations
	        int hashLength = 64; // Generate a 32 byte (256 bit) hash
	      
	        Argon2BytesGenerator generator = new Argon2BytesGenerator();
	        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
	                .withSalt(generateSalt16Byte()) // You need to generate a salt
	                .withParallelism(parallelism) // Parallelism factor
	                .withMemoryAsKB(memory) // Memory cost
	                .withIterations(iterations); // Number of iterations

	        generator.init(builder.build());
	        byte[] result = new byte[hashLength];
	        generator.generateBytes(password.toCharArray(), result);
	        return Base64.getEncoder().encodeToString(result);
	    }
	
	 
	 private static byte[] generateSalt16Byte() {
		    SecureRandom secureRandom = new SecureRandom();
		    byte[] salt = new byte[16];
		    secureRandom.nextBytes(salt);
		        
		    return salt;
		}
	 
}
