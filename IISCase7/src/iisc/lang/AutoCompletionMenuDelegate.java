package iisc.lang;

import java.util.List;

public interface AutoCompletionMenuDelegate {
    public List<String> autoCompletionMenuGetMatchingWordsForPartialWord(String partialWord);
    public void autoCompletionMenuWillDisplay();
}
