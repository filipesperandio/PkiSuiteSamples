class Config(object):
    DEBUG = False
    TESTING = False

    # Trust in Lacuna Test PKI (for development purposes only!)
    TRUST_LACUNA_TEST_ROOT = True

    # --------------------------------------------------------------------------
    # REST PKI
    # --------------------------------------------------------------------------

    # ========================================================
    #    >>>> PASTE YOUR REST PKI ACCESS TOKEN BELOW <<<<
    # ========================================================
    REST_PKI_ACCESS_TOKEN='6vQ7ZL8UCH1aDBrpuUXc3bdKJJo7ijMWbh90y-5R8h9G5Xc9Ut2-Ma8g7RFq6i87XeP2koaeisd85o5WZGUf4YmYVinAtQLKZXMfVQOVGayVu5EXfKHuR-b1Stp32bFaYa8uGcopuDy5eYFu56ESm5-j-32uS62CUHtKGn7Yn8hWzZrVDJHFD0I8FSbyVo7JqbPCeXvwTrigJiQJQ76MWe-EcQCZcBeZshIGWygDEve91AogfY5NLJ6s627xJJbCl0U0cd2oLJGfZAsF-FXHN8Wkw7rFiZKEmOcaqlu8b_85hTvv4N-eewhnGRNdM8mMdRC_-KIEh1QBWiJawiTgCKhJIfz_PW7ugieOicmmtyFZMMztiUj6cXHBHR7_l7CxCGo4pclUubkPDoqZuHZk8LIcUJcfrUd15c8s9FYXrD_x_MsdmUS2nG637y8L_gSUgg-aZKr0go6zyuR0TQEuea-FiHatFkzT3pJDfPHpiYqQZn2RHxlDmEiDZjbBkdtUpYIKCQ'
    # This is a TRIAL token. It will be expired at 30/09/2022.
    # If the REST PKI sample doesn't work, please contact our support by email:
    # suporte@lacunasoftware.com

    # In order to use this sample on a "on premises' installation of REST PKI,
    # fill the field below with the URL address of your REST PKI installation
    # (with the trailing '/' character).
    REST_PKI_ENDPOINT = 'https://pki.rest/'

    # --------------------------------------------------------------------------
    # Amplia
    # --------------------------------------------------------------------------

    # The CA's id that will be used to issue a certificate using Amplia. We
    # have configured to the sample CA from sample subscription for these
    # samples.
    AMPLIA_CA_ID = 'eaffa754-1fb5-474a-b9ef-efe43101e89f'

    # ========================================================
    #     >>>> PASTE YOUR AMPLIA API KEY BELOW <<<<
    # ========================================================
    AMPLIA_API_KEY = 'pki-suite-samples-02|fc4906d4065c1a48890e85047ae5525a1ef0bc2f3871e3c5fdd092795557405a'
    # This is a TRIAL API key to use Amplia. It will expire at 30/09/2022.
    # If the Amplia's samples do not work please contact our support by email:
    # suporte@lacunasoftware.com

    # In order to use this sample on a "on premises" installation of
    # Amplia, fill the field below with the URL address of your REST PKI
    # installation (with the trailing '/' character).
    AMPLIA_ENDPOINT = 'https://amplia.lacunasoftware.com/'

    # --------------------------------------------------------------------------
    # PKI Express
    # --------------------------------------------------------------------------

    # List of custom trusted roots. In this sample, we will get the certificate
    # files on static/ folder.
    PKI_EXPRESS_TRUSTED_ROOTS = []

    # Offline mode. Set this, if you want PKI Express to run on offline mode.
    # This mode is useful when there is no network available.
    PKI_EXPRESS_OFFLINE = False

    # --------------------------------------------------------------------------
    # Web PKI
    # --------------------------------------------------------------------------
    WEB_PKI_LICENSE = None
