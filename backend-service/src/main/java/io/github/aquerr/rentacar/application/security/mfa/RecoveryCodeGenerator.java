package io.github.aquerr.rentacar.application.security.mfa;

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Taken from dev.samstevens.totp.recovery.RecoveryCodeGenerator and adapted for parameterization.
 *
 * Generates recovery codes in format: 4ckn-xspn-et8t-xgr0
 */
public class RecoveryCodeGenerator
{
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int CODE_LENGTH_DEFAULT = 16;
    private static final int GROUPS_COUNT_DEFAULT = 4;

    private Random random = new SecureRandom();

    private final int codeLength;
    private final int groupsCount;

    public RecoveryCodeGenerator()
    {
        this.codeLength = CODE_LENGTH_DEFAULT;
        this.groupsCount = GROUPS_COUNT_DEFAULT;
    }

    /**
     * @param codeLength recovery code length exclusive separators
     * @param groupsCount the number of groups
     */
    public RecoveryCodeGenerator(int codeLength, int groupsCount)
    {
        this.codeLength = codeLength;
        this.groupsCount = groupsCount;
    }

    public String[] generateCodes(int amount) {
        // Must generate at least one code
        if (amount < 1) {
            throw new InvalidParameterException("Amount must be at least 1.");
        }

        // Create an array and fill with generated codes
        String[] codes = new String[amount];
        Arrays.setAll(codes, i -> generateCode());

        return codes;
    }

    private String generateCode() {
        final StringBuilder code = new StringBuilder(codeLength + (codeLength/groupsCount) - 1);

        for (int i = 0; i < codeLength; i++) {
            // Append random character from authorized ones
            code.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);

            // Split code into groups for increased readability
            if ((i+1) % groupsCount == 0 && (i+1) != codeLength) {
                code.append("-");
            }
        }

        return code.toString();
    }
}
