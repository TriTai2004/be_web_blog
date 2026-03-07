package app.demo.util;

import java.text.Normalizer;

import app.demo.repository.ArticleRepository;
import app.demo.repository.CategoryRepository;


public class SlugUtil {

	public static String toSlug(String title) {
	    if (title == null) return "";

	    return Normalizer.normalize(title.toLowerCase(), Normalizer.Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	            .replaceAll("đ", "d")
	            .replaceAll("[^a-z0-9\\s-]", "")
	            .replaceAll("\\s+", "-")
	            .replaceAll("-{2,}", "-")
	            .replaceAll("^-|-$", "");
	}

	 /**
     * Sinh slug duy nhất, check toàn bộ DB để không trùng.
     * @param title Tiêu đề gốc
     * @param categoryRepository Repository để check slug tồn tại
     * @return slug duy nhất
     */
    public static String generateUniqueSlug(String title, CategoryRepository categoryRepository) {
        String baseSlug = toSlug(title);
        String slug = baseSlug;
        int counter = 1;

        // check trong DB cho đến khi slug chưa tồn tại
        while (categoryRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    public static String generateUniqueSlugArticle(String title, ArticleRepository articleRepository) {
        String baseSlug = toSlug(title);
        String slug = baseSlug;
        int counter = 1;

        // check trong DB cho đến khi slug chưa tồn tại
        while (articleRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }
    
    // Kiểm tra slug hợp lệ
    public static boolean isValid(String slug) {
        return slug != null && slug.matches("^[a-z0-9-]+$");
    }
}
