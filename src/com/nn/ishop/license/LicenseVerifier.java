/* ***************************************************************************/
/* File Description  : EncryptionUtil                                        */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright � 2004-2010 by DONGA JSC                    */
/*                     All Rights Reserved                                   */
/* Company Name      : DONGA JSC.                                            */
/* Original Filename : EncryptionUtil.java                                   */
/* Product Version   : 1.0                                                   */
/* Product Name      : STAND ALONE LICENSE API                               */
/*****************************************************************************/
package com.nn.ishop.license;


import java.security.PublicKey;


/**
 * Verify license key
 * @author nvnghia
 *
 */
public class LicenseVerifier {

	public static IShopLicense validateLicense(
			  String licenseEncoded
			, PublicKey publicKey)
		throws LicenseException 
	{
		String licenseKey = "";
		String signature = "";

		try 
		{
			EncryptionUtil signer = new EncryptionUtil();
			
			// the encoded license key consists of the
			// base64 encoded license key and the signature
			// generated by the private key, separated by a #
			String tokens[] = licenseEncoded.split("#");
			if ( tokens.length != 2 )
				throw new LicenseException("LicenseKey is invalid");
			licenseKey = Base64Coder.decode(tokens[0]);
			signature = tokens[1];
			
			/** Use Signature and public key to verify licenseKey */
			if ( !signer.verify( licenseKey, signature, publicKey ) )					
				throw new LicenseException("Signature verify error, license cannot be verified.");
			
			/** Prepare License object and return to invoker */
			IShopLicense lic = null;
			lic = new IShopLicense(licenseKey);
			return lic;
		}
		catch ( Exception e)
		{
			e.printStackTrace();
			throw new LicenseException(e.toString());
			
		}
	}	
	/**
	 * Validate a license string loaded by reading the content 
	 * of a license file
	 * @param licenseEncoded encoded license String 
	 * represent for license with its signature 
	 * (02 part distinguished by "#")
	 * @param publicKeyFile path to public key file
	 * @param isServerValidate license is a server license
	 * @return
	 * @throws LicenseException
	 * @deprecated Unused when pk has been embedded into license file
	 */
	public static IShopLicense validateLicense(
			  String licenseEncoded
			, String publicKeyFile)
		throws LicenseException 
	{
		String licenseKey = "";
		String signature = "";

		try 
		{
			EncryptionUtil signer = new EncryptionUtil();
			
			// the encoded license key consists of the
			// base64 encoded license key and the signature
			// generated by the private key, separated by a #
			String tokens[] = licenseEncoded.split("#");
			if ( tokens.length != 2 )
				throw new LicenseException("LicenseKey is invalid");
			licenseKey = Base64Coder.decode(tokens[0]);
			signature = tokens[1];
			
			/** Use Signature and public key to verify licenseKey */
			if ( !signer.verify( licenseKey, signature, publicKeyFile ) )					
				throw new LicenseException("Signature verify error, license cannot be verified.");
			
			/** Prepare License object and return to invoker */
			IShopLicense lic = null;
			lic = new IShopLicense(licenseKey);
			return lic;
		}
		catch ( Exception e)
		{
			e.printStackTrace();
			throw new LicenseException(e.toString());
			
		}
	}	

//	private static String readSignature(String signatureFile) throws IOException {
//		FileInputStream sigfis = new FileInputStream(signatureFile);
//        byte[] sigToVerify = new byte[sigfis.available()];
//        sigfis.read(sigToVerify);
//        sigfis.close();
//        return new String(sigToVerify);
//	}

	/**
	 * Read a Public Key from a file
	 * @param URI the name of the file containing the public key
	 * @return PublicKey - the public key instance
	 * @throws LicenseException
	 */
	public static PublicKey readPublicKey( String URI ) throws LicenseException
	{
		try
		{
			EncryptionUtil keyUtil = new EncryptionUtil();
			PublicKey publicKey = keyUtil.readPublicKey( URI );
			return publicKey;
		}
		catch (Exception e)
		{
			throw new LicenseException("Unable to read Key File:" + e.getMessage());
		}
	}

}