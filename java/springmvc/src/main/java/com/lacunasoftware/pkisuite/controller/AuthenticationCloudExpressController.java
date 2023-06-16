package com.lacunasoftware.pkisuite.controller;

import com.lacunasoftware.pkiexpress.*;
import com.lacunasoftware.pkisuite.model.express.AuthenticationCloudExpressModel;
import com.lacunasoftware.pkisuite.model.express.PadesCloudOauthModel;
import com.lacunasoftware.pkisuite.util.StorageMock;
import com.lacunasoftware.pkisuite.util.Util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.lacunasoftware.pkiexpress.AuthCompleteResult;
import com.lacunasoftware.pkiexpress.AuthStartResult;
import com.lacunasoftware.pkiexpress.Authentication;
import com.lacunasoftware.pkiexpress.ValidationResults;


/**
 * This sample is responsible to perform a OAuth flow to communicate with PSCs to perform a
 * signature. To perform this sample it's necessary to configure PKI Express with the credentials of
 * the services by executing the following sample:
 *
 *    pkie config --set trustServices:<provider>:<configuration>
 *
 * All standard providers:
 *    - BirdId
 *    - ViDaaS
 *    - NeoId
 *    - RemoteId
 *    - SafeId
 * It's possible to create a custom provider if necessary.
 *
 * All configuration available:
 *    - clientId
 *    - clientSecret
 *    - endpoint
 *    - provider
 *    - badgeUrl
 *    - protocolVariant (error handling, normally it depends on the used provider)
 *
 * This sample will only show the PSCs that are configured.
 */
@Controller
public class AuthenticationCloudExpressController {

	// Redirect URL where it's accessed after OAuth flow is finished.
	private final String REDIRECT_URL = "http://localhost:60695/authentication-cloud-express/complete";

	/**
	 * This action will render a page that request a CPF to the user. This CPF is used to discover
	 * which PSCs have a certificate containing that CPF.
	 */
	@GetMapping("/authentication-cloud-express")
	public ModelAndView get(
	) {
		return new ModelAndView("/authentication-cloud-express/index");
	}

    /**
	 * This action will be called after the user press the button "Search" on index page. It will
	 * search for all PSCs that have a certificate with the provided CPF. Thus, it will start the
	 * authentication process and return a URL to redirect the user to perform the authentication.
	 *
	 * After this action the user will be redirected, and to store the local data (fileId) to be user
	 * after the user returns to your application. We use the parameter "customState", the last
	 * parameter of the method discoverByCpfAndStartAuth(). This parameter will be recovered in the
	 * next action.
	 */
	@PostMapping("/authentication-cloud-express/discover")
	public ModelAndView discover(
		@RequestParam(value="cpf") String cpf
	) throws IOException {

        
        // Get an instance of the Authentication class, responsible for contacting PKI Express to
		// start and complete the authentication operation.
        Authentication auth = new Authentication();

		// Set PKI default options. (see Util.java)
		Util.setPkiDefaults(auth);

		// Start the authentication. Receive as response a AuthStartResult instance containing
		// the following fields:
		// - nonce: The nonce to be signed. This value is also used on "complete" action;
		// - digestAlgorithm: The digest algorithm that will inform the Web PKI component to
		// compute the signature.
		AuthStartResult result = auth.start();

		// Set PKI default options. (see Util.java)
		Util.setPkiDefaults(auth);

		// Set the nonce. This value is generated on "start" action and passed by a hidden field.
		auth.setNonce(result.getNonce());

		// Process cpf, removing all formatting.
		String plainCpf = cpf.replaceAll("[.-]", "");

		// Get an instance of the TrustServiceManager class, responsible for communicating with PSCs
		// and handling the OAuth flow.
		TrustServicesManager manager = new TrustServicesManager();

		// Discover PSCs and receive a URL to redirect the user to perform the OAuth authentication
		// page. As mentioned before, we pass the id of the file to be signed as the last parameter
		// of the following method. The next action will recover this information.
		List<TrustServiceAuthParameters> services = manager.discoverByCpfAndStartAuth(plainCpf, REDIRECT_URL, TrustServiceSessionTypes.AUTHENTICATION_SESSION, result.getNonce());

		// Render complete page.
		AuthenticationCloudExpressModel model = new AuthenticationCloudExpressModel();
		model.setServices(services);
		model.setCpf(cpf);
		return new ModelAndView("/authentication-cloud-express/discover", "model", model);
	}

    @GetMapping("/authentication-cloud-express/complete")
	public ModelAndView complete(
		@RequestParam(value="code", required = false) String authorizationCode,
        @RequestParam(value="state", required = false) String state
	) throws IOException {

        TrustServicesManager manager = new TrustServicesManager();

        // Complete the authentication process, recovering the session info to be used on the
        // signature and the custom state (fileId).
        TrustServiceSessionResult result = manager.completeAuth(authorizationCode, state);
        String session = result.getSession();

		// // Set the signature.
		// auth.setSignature(signature);

		// // Complete the authentication. Receive as response a AuthCompleteResult instance containing
		// // the following fields:
		// // - The certificate information;
		// // - The validation results;
		// AuthCompleteResult result = auth.complete();

		// // Check the authentication result,
		// ValidationResults vr = result.getValidationResults();
		// if (!vr.isValid()) {

		// 	// If the authentication was not successful, we render a page showing that what went
		// 	// wrong.

		// 	// The toString() method of the ValidationResults object can be used to obtain the
		// 	// checks performed, but the string contains tabs and new line characters for
		// 	// formatting, which we'll convert to <br>'s and &nbsp;'s.
		// 	String vrHtml = vr.toString()
		// 		.replaceAll("\n", "<br>")
		// 		.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		// 	model.addAttribute("vrHtml", vrHtml);

		// 	// Render the authentication failed page (templates/authentication-failed.html)
		// 	return "authentication-express/failed";
		// }

		// At this point, you have assurance that the certificate is valid. Now, you'd typically
		// query your database for a user that matches one of the certificate's fields, such as
		// userCert.getEmailAddress() or userCert.getPkiBrazil().getCpf() (the actual field to be
		// used as key depends on your application's business logic) and set the user as
		// authenticated with whatever web security framework your application uses. For
		// demonstration purposes, we'll just render the user's certificate information.
		// model.addAttribute("userCert", result.getCertificate());

		// Render the authentication succeeded page (templates/authentication-success.html)
		return new ModelAndView("authentication-express/success");

    }
}