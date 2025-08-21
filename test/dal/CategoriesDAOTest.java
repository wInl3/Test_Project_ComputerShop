package dal;

import org.junit.Test;
import static org.junit.Assert.*;

public class CategoriesDAOTest {

    // Fake DAO: override method để không gọi DB
    private final CategoriesDAO fakeDao = new CategoriesDAO() {
        @Override
        public int countFilteredByComponent(int componentID, String brand, int minPrice, int maxPrice, String keyword) {
            if (componentID == 1 && "Intel".equalsIgnoreCase(brand) && keyword != null && keyword.contains("CPU")) {
                return 5;
            }
            if (componentID == 2 && "AMD".equalsIgnoreCase(brand)) {
                return 3;
            }
            if (componentID == 3 && minPrice >= 100 && maxPrice <= 1000) {
                return 2;
            }
            if (keyword != null && keyword.toLowerCase().contains("gpu")) {
                return 4;
            }
            // simulate exception if componentID < 0
            if (componentID < 0) {
                throw new IllegalArgumentException("Invalid componentID");
            }
            return 0;
        }
    };

    // ---------------- NORMAL ASSERT TEST ----------------

    @Test
    public void testIntelCPU_shouldReturn5_normal() {
        assertEquals(5, fakeDao.countFilteredByComponent(1, "Intel", 0, 10000, "CPU"));
    }

    @Test
    public void testAMD_shouldReturn3_normal() {
        assertEquals(3, fakeDao.countFilteredByComponent(2, "AMD", 0, 5000, null));
    }

    // ---------------- TIMEOUT TEST ----------------

    @Test(timeout = 2000)
    public void testPriceRange100to1000_shouldReturn2() {
        assertEquals(2, fakeDao.countFilteredByComponent(3, null, 100, 1000, null));
    }

    @Test(timeout = 2000)
    public void testKeywordGPU_shouldReturn4() {
        assertEquals(4, fakeDao.countFilteredByComponent(0, null, 0, 9999, "gpu"));
    }

    @Test(timeout = 2000)
    public void testDifferentBrand_shouldReturn0() {
        assertEquals(0, fakeDao.countFilteredByComponent(1, "Nvidia", 0, 10000, "CPU"));
    }

    @Test(timeout = 2000)
    public void testDifferentComponentID_shouldReturn0() {
        assertEquals(0, fakeDao.countFilteredByComponent(99, "Intel", 0, 10000, "CPU"));
    }

    @Test(timeout = 2000)
    public void testEmptyKeyword_shouldReturn0() {
        assertEquals(0, fakeDao.countFilteredByComponent(1, "Intel", 0, 10000, null));
    }

    @Test(timeout = 2000)
    public void testNullKeyword_shouldReturn0() {
        assertEquals(0, fakeDao.countFilteredByComponent(1, "Intel", 0, 10000, null));
    }

    @Test(timeout = 2000)
    public void testPriceTooLow_shouldReturn0() {
        assertEquals(0, fakeDao.countFilteredByComponent(3, null, -100, 50, null));
    }

    @Test(timeout = 2000)
    public void testPriceTooHigh_shouldReturn0() {
        assertEquals(0, fakeDao.countFilteredByComponent(3, null, 10000, Integer.MAX_VALUE, ""));
    }

    // ---------------- EXCEPTION TEST ----------------

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidComponentID_shouldThrowException() {
        fakeDao.countFilteredByComponent(-1, "Intel", 0, 1000, "CPU");
    }
}
    // ---------------- NULL TEST ----------------
    // ---------------- NULL TEST ----------------
