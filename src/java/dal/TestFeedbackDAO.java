package dal;

import models.Feedback;
import java.util.List;

public class TestFeedbackDAO {
    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
        int testCategoryId = 24; // Thay bằng ID category thực tế có dữ liệu feedback
        List<Feedback> feedbacks = dao.getFeedbackByCategoryId(testCategoryId);
        System.out.println("Feedback for categoryID=" + testCategoryId + ":");
        for (Feedback fb : feedbacks) {
            System.out.println(fb);
        }
        if (feedbacks.isEmpty()) {
            System.out.println("No feedback found for this category.");
        }
    }
}
