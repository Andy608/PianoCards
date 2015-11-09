package andrewrimpici.knowyournotes.core;

public class BackgroundDisplayUpdater {

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

    private int transitionTimeMilliseconds;
    private float timeElapsed;
    private float percentage;
    private int currentColorIndex, nextColorIndex;

    private Color targetColor;

    public BackgroundDisplayUpdater(int colorTransitionMilliseconds) {
        transitionTimeMilliseconds = colorTransitionMilliseconds;
        nextColorIndex = currentColorIndex + 1;
        targetColor = new Color(transitionColors[0]);
    }

    public void update(float deltaTime) {
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

    public Color getTargetColor() {
        return targetColor;
    }
}
