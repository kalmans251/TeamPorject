package com.himedia.item.itemMain;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.himedia.item.entity.Item;
import com.himedia.item.entity.enums.CategoryEnum1;
import com.himedia.item.entity.enums.CategoryEnum2;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ItemMainService {
	
	private final ItemMainRepository itemMainRepository;
	
	public ItemMainService(ItemMainRepository itemMainRepository) {
		this.itemMainRepository = itemMainRepository;
	}
	

	//카테고리 정렬
	public Page<Item> findItemsByCategory(CategoryEnum1 categoryEnum1, CategoryEnum2 categoryEnum2, int page) {
		
		int size = 9;
		Pageable pageable= PageRequest.of(page-1, size);
		
		return itemMainRepository.findByCategoryEnum1OrCategoryEnum2(categoryEnum1.toString(), categoryEnum2.toString(), pageable);
	}


	public Page<Item> findItemsByCategory(String category,String sort,int page) {
		int size = 9;
		
		List<Sort.Order> sorts = new ArrayList(); 
		
		if(sort.equals("1")) {
			sorts.add(Sort.Order.desc("regDate")); //최신순
		}else if(sort.equals("2")) {
			sorts.add(Sort.Order.desc("price")); //높은가격순
		}else if(sort.equals("3")) {
			sorts.add(Sort.Order.asc("price")); //낮은가격순
		}else if(sort.equals("4")) {
			sorts.add(Sort.Order.desc("")); //인기순
		}
		
		Pageable pageable= PageRequest.of(page-1, size, Sort.by(sorts));
		
		Specification<Item> spe = search(category);
		
		return itemMainRepository.findAll(spe, pageable);
	}
	
	
	private Specification<Item> search(String kw) {
    	
        return new Specification<>() {
        	
            private static final long serialVersionUID = 1L;
            
            @Override
            public Predicate toPredicate(Root<Item> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
                query.distinct(true);  // 중복을 제거 
                
                return cb.or(
                		cb.like(q.get("category1"), "%" + kw + "%"), // category1(대분류)
                        cb.like(q.get("category2"), "%" + kw + "%")      // category2(소분류)
                        );   // 답변 작성자 
            }
        };
    }
	
	
}

