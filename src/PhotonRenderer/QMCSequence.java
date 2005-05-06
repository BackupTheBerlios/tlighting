package PhotonRenderer;

import java.awt.*;
import drawing_prog.*;

public interface QMCSequence {
    void reset();

    double[] getNext();
}