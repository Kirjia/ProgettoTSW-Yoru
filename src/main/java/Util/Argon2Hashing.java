package Util;

import java.security.SecureRandom;
import java.util.Base64;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;




public class Argon2Hashing {
	
	
	 public static String hashPassword(String password) {
	      
	        // Set realistic values for Argon2 parameters
	        int parallelism = 2; // Use 2 threads
	        int memory = 65536; // Use 64 MB of memory
	        int iterations = 44; // Run 3 iterations
	        int hashLength = 256; // Generate a 32 byte (256 bit) hash
	        byte[] salt = generateSalt32Byte();
	      
	        Argon2BytesGenerator generator = new Argon2BytesGenerator();
	        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
	        		.withVersion(Argon2Parameters.ARGON2_VERSION_13)
	                .withSalt(salt) // You need to generate a salt
	                .withParallelism(parallelism) // Parallelism factor
	                .withMemoryAsKB(memory) // Memory cost
	                .withIterations(iterations); // Number of iterations
	        
	        generator.init(builder.build());
	        
	        byte[] result = new byte[hashLength];
	        generator.generateBytes(password.toCharArray(), result);
	        String passwordHashed = Base64.getEncoder().encodeToString(result);
	        String saltBase = Base64.getEncoder().encodeToString(salt);
	        //return String.format("$argon2id$v=%d$m=%d,t=%d,p=%d$%s$%s", Argon2Parameters.ARGON2_id, memory, iterations, parallelism, saltBase, passwordHashed);
	        return String.format("$argon2id$v=%d$m=%d,t=%d,p=%d$%s$%s", Argon2Parameters.ARGON2_VERSION_13, memory, iterations, parallelism, saltBase, passwordHashed);
	        	
	        	
	        	
	    }
	 
	 
	 public static Boolean checkPass(String password, String toCheck) {
		 String[] argon2params = password.split("\\$");
		 String[]paramString = argon2params[3].split(",");
		 int memory = Integer.parseInt(paramString[0].substring(2));
		 int iterations = Integer.parseInt(paramString[1].substring(2));
		 int parallelism = Integer.parseInt(paramString[2].substring(2));
		 byte[] salt = Base64.getDecoder().decode(argon2params[4]);
		 String hashedPassword = argon2params[5];
		 int hashLength = 256;
		 
		 
		 Argon2BytesGenerator generator = new Argon2BytesGenerator();
		 Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
				 	.withVersion(Argon2Parameters.ARGON2_VERSION_13)
	                .withSalt(salt) // You need to generate a salt
	                .withParallelism(parallelism) // Parallelism factor
	                .withMemoryAsKB(memory) // Memory cost
	                .withIterations(iterations); // Number of iterations
	        
		 generator.init(builder.build());
	        
		 byte[] result = new byte[hashLength];
         generator.generateBytes(toCheck.toCharArray(), result);
         String passwordHashed = Base64.getEncoder().encodeToString(result);
		 
		 System.out.println(hashedPassword);
		 System.out.println(passwordHashed);
		 return hashedPassword.equals(passwordHashed);
		 
		 
		
	 }
	
	 
	 private static byte[] generateSalt32Byte() {
		    SecureRandom secureRandom = new SecureRandom();
		    byte[] salt = new byte[32];
		    secureRandom.nextBytes(salt);
		        
		    return salt;
		}
	 
	 public static String generateToken() {
		 SecureRandom secureRandom = new SecureRandom();
		 byte[] token = new byte[64];
		 secureRandom.nextBytes(token);
		 return Base64.getEncoder().encodeToString(token);
	 }
	 
}
