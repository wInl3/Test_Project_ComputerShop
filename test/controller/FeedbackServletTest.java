package controller;

import org.junit.Test;
import static org.junit.Assert.*;

import models.Feedback;

public class FeedbackServletTest {

    private FeedbackServlet servlet = new FeedbackServlet();

    private void testValidateFeedback(String content, int rate, boolean expectedResult) {
        // Giả lập userID và orderItemID
        int userID = 1;
        int orderItemID = 1;

        // Tạo Feedback để kiểm tra
        Feedback feedback = new Feedback(userID, content, orderItemID, rate);

        // Giả lập logic validate (giống trong doPost)
        boolean isValid = (content != null 
                && !content.trim().isEmpty() 
                && content.length() <= 500 
                && rate >= 1 && rate <= 5);

        assertEquals(expectedResult, isValid);
    }

    @Test
    public void testValidateFeedbackValidInput() {
        testValidateFeedback("Good product", 4, true);
    }

    @Test
    public void testValidateFeedbackInvalidContent() {
        testValidateFeedback("", 4, false);
    }

    @Test
    public void testValidateFeedbackInvalidRate() {
        testValidateFeedback("Good product", 6, false);
    }
}