package com.hotel_transylvania.entities;

import com.hotel_transylvania.enums.TipoQuarto;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuartoSuite extends Quarto {
    
    @OneToMany(mappedBy = "quarto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoExtra> servicosExtras = new ArrayList<>();

    public QuartoSuite() {
        super();
        this.setTipo(TipoQuarto.SUITE);
    }

    public QuartoSuite(Integer numero, BigDecimal preco, List<ServicoExtra> servicosExtras) {
        super(numero, preco, TipoQuarto.SUITE);
        if (servicosExtras != null) {
            this.servicosExtras = servicosExtras;
            this.servicosExtras.forEach(servico -> servico.setQuarto(this));
        }
    }

    @Override
    public BigDecimal calcularPrecoTotal() {
        BigDecimal totalServicos = servicosExtras.stream()
                .map(ServicoExtra::getPrecoAdicional)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return getPreco().add(totalServicos);
    }

    @Override
    public String exibirInfo() {
        StringBuilder servicos = new StringBuilder();
        servicosExtras.forEach(servico -> 
            servicos.append(servico.getNome()).append(", "));
        
        return String.format("Suíte #%d - Preço Base: R$%.2f - Serviços: %s - %s",
                getNumero(), 
                getPreco(),
                servicos.length() > 0 ? servicos.substring(0, servicos.length() - 2) : "Nenhum",
                getDisponivel() ? "Disponível" : "Ocupado");
    }

    // Getters e Setters corretamente tipados
    public List<ServicoExtra> getServicosExtras() {
        return servicosExtras;
    }

    public void setServicosExtras(List<ServicoExtra> servicosExtras) {
        this.servicosExtras.clear();
        if (servicosExtras != null) {
            this.servicosExtras.addAll(servicosExtras);
            this.servicosExtras.forEach(servico -> servico.setQuarto(this));
        }
    }

    public void adicionarServicoExtra(ServicoExtra servico) {
        servico.setQuarto(this);
        this.servicosExtras.add(servico);
    }
}