package com.fithub.model.product;

import com.fithub.model.accounting.Account;
import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@Table(name = "product_categories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "product_category_benefits",
        joinColumns = @JoinColumn(name = "product_category_id"),
        inverseJoinColumns = @JoinColumn(name = "benefit_id")
    )
    private Set<Benefit> benefits;

    @ManyToOne
    @JoinColumn(name = "income_account_id")
    private Account incomeAccount;

    @ManyToOne
    @JoinColumn(name = "expense_account_id")
    private Account expenseAccount;
}
