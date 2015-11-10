package andrewrimpici.knowyournotes.core;

public final class BackgroundDisplayUpdater {

    //TODO: EVENTUALLY THIS CLASS WILL NOT BE STATIC AND YOU WILL BE ABLE TO PASS IN AN ARRAY OF CUSTOM COLORS FOR THE CIRCLE EFFECT OR ANOTHER EFFECT.

    private static final Color[] transitionColors = new Color[] {
            new Color(0xFD / 255f, 0x53 / 255, 0x08 / 255, 1.0f),
            new Color(0xFB / 255f, 0x99 / 255f, 0x02 / 255f, 1.0f),
            new Color(0xF9 / 255f, 0xBC / 255f, 0x02 / 255f, 1.0f),
            new Color(0xFF / 255f, 0xFE / 255f, 0x32 / 255f, 1.0f),
            new Color(0xD0 / 255f, 0xE9 / 255f, 0x2B / 255f, 1.0f),
            new Color(0x65 / 255f, 0xB0 / 255f, 0x31 / 255f, 1.0f),
            new Color(0x02 / 255f, 0x91 / 255f, 0xCD / 255f, 1.0f),
            new Color(0x01 / 255f, 0x47 / 255f, 0xFE / 255f, 1.0f),
            new Color(0x3E / 255f, 0x01 / 255f, 0xA4 / 255f, 1.0f),
            new Color(0x87 / 255f, 0x01 / 255f, 0xB0 / 255f, 1.0f),
            new Color(0xA7 / 255f, 0x19 / 255f, 0x4B / 255f, 1.0f)
    };

    private static int transitionTimeMilliseconds = 5000;
    private static float timeElapsed;
    private static float percentage;
    private static int currentColorIndex, nextColorIndex = 1;

    private static Color targetColor = new Color(transitionColors[0]);

    public static void update(float deltaTime) {
        deltaTime *= 1000;

        timeElapsed = (timeElapsed + deltaTime);

        if (timeElapsed > transitionTimeMilliseconds) {
            currentColorIndex = (currentColorIndex + 1) % transitionColors.length;
            nextColorIndex = (nextColorIndex + 1) % transitionColors.length;
        }
        timeElapsed %= transitionTimeMilliseconds;
        percentage = (timeElapsed / transitionTimeMilliseconds);

        targetColor.set((transitionColors[currentColorIndex].r * (1 - percentage) + (transitionColors[nextColorIndex].r * percentage)),
                        (transitionColors[currentColorIndex].g * (1 - percentage) + (transitionColors[nextColorIndex].g * percentage)),
                        (transitionColors[currentColorIndex].b * (1 - percentage) + (transitionColors[nextColorIndex].b * percentage)),
                        1.0f);
    }

    public static Color getTargetColor() {
        return targetColor;
    }
}
