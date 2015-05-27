/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.micronixnetwork.cdk.domain;

import it.micronixnetwork.application.plugin.crude.annotation.Conditional;
import it.micronixnetwork.application.plugin.crude.annotation.ToInput;
import it.micronixnetwork.application.plugin.crude.annotation.ToList;
import it.micronixnetwork.application.plugin.crude.annotation.ToView;
import it.micronixnetwork.application.plugin.crude.annotation.ValidField;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.HiddenRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.SelectRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.application.plugin.crude.annotation.renderer.YesNoRenderer;
import it.micronixnetwork.application.plugin.crude.model.ViewModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author kobo
 */
@Entity
@Table(name = "indicatori2")

public class MappingBaseDetail implements ViewModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdItem")
    public Integer id;

    @ToList(filtered = true, listed = false, filterRule = "mapByQuery('select a.id,a.descrizione from MappingBaseDetail$_Azienda a order by a.descrizione asc')")
    @ToInput
    @ToView
    @ValidField(empty = false, type = "")
    @SelectRenderer(activeOnChange = "sottoconto,centroCosto,voceSpesa", map = "mapByQuery('select a.id,a.descrizione,a.si from MappingBaseDetail$_Azienda a order by a.descrizione asc')", viewRule = "azienda.descrizione", startValue = "{' ',''}")
    @Column(name = "IdAzienda")
    public Integer idAzienda;
    
    @OneToOne
    @JoinColumn(name = "idAzienda", insertable = false, updatable = false)
    public _Azienda azienda;

    @ToList(cellRule = "_voceSpesa.descrizione")
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select v.codice,v.descrizione from MappingBaseDetail$_VociSpesa v where idAzienda=:idAzi order by v.descrizione asc',#{\"idAzi\":idAzienda})", viewRule = "_voceSpesa.descrizione", dependFrom = "idAzienda")
    @Column(name = "VoceSpesa")
    public String voceSpesa;

    @ToList
    @ToInput
    @ToView
    @SelectRenderer(map = "#{'A':'A','B':'B','C':'C','D':'D','E':'E','F':'F','G':'G','H':'H','I':'I','L':'L','M':'M','N':'N','O':'O','P':'P','Q':'Q','R':'R','S':'S','T':'T','U':'U','V':'V','Z':'Z'}", startValue = "{' ',''}")
    @Column(name = "Prefisso")
    public String prefisso;

    @ToList(filtered = true, hidden = true)
    @ToInput
    @HiddenRenderer
    @Column(name = "Item")
    public String item;

    
    @Column(name = "Moltiplicatore")
    public String moltiplicatore;

    @ToList
    @Column(name = "Diretto")
    public Boolean diretto;

    @ToList(cellRule = "_sottoconto.descrizione")
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select s.codice,s.descrizione from MappingBaseDetail$_Sottoconto s where idAzienda=:idAzi order by s.descrizione asc',#{\"idAzi\":idAzienda})", viewRule = "_sottoconto.descrizione", dependFrom = "idAzienda")
    @Column(name = "Sottoconto")
    public String sottoconto;

    @Conditional(rule = "azienda.si=='NAV'")
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select c.codice,c.descrizione from MappingBaseDetail$_CentriCosto c where idAzienda=:idAzi order by c.descrizione asc',#{\"idAzi\":idAzienda})", viewRule = "_centroCosto.descrizione", dependFrom = "idAzienda")
    @Column(name = "CentroCosto")
    public String centroCosto;

    @Conditional(rule = "azienda.si=='ACG'")
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select u.id,u.nome from MappingBaseDetail$_Ufficio u order by u.nome asc')", viewRule = "_ufficio.nome")
    @Column(name = "Ufficio")
    public String ufficio;

    

    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select m.id,m.descrizione from MappingBaseDetail$_Marchio m order by m.descrizione asc')", viewRule = "_marchio.descrizione")
    @Column(name = "Brand")
    public String brand;

    @ToInput
    @ToView
    @Column(name = "Genere")
    //@SelectRenderer(map = "#{'0':'Generico','1':'Uomo','2':'Donna','B':'Bags','M':'Men','P':'Precollezione','U':'Unisex','W':'Women','WN':'Women Navy'}")
    @SelectRenderer(map = "mapByQuery('select g.id,g.nome from MappingBaseDetail$_Genere g order by g.nome asc')", viewRule = "_genere.nome")
    public String genere;
    
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select t.id,t.nome from MappingBaseDetail$_TipoProdotto t order by t.nome asc')", viewRule = "_tipoProdotto.nome")
    @Column(name = "TipoProdotto")
    public String tipoProdotto;
    
    @ToInput
    @ToView
    @SelectRenderer(map = "mapByQuery('select s.id,s.nome from MappingBaseDetail$_Stagione s order by s.nome asc')", viewRule = "_stagione.nome")
    @Column(name = "Stagione")
    public String stagione;
    
    @ToInput
    @ToView
    @YesNoRenderer(values = "1|0",viewRule = "#{\"1\":{'Si','img_green'}, \"0\":{'No','img_red'}}")
    @Column(name = "Consociata")
    public Short consociata;
    
    @ToList(cellRule = "getText('{0,number,percent}',{coefficiente})")
    @ToInput
    @ToView
    @ValidField(empty = false, type = ValidField.FLOAT_VALIDATION)
    @TextRenderer(viewRule = "getText('{0,number,percent}',{coefficiente})",type =TextRenderer.REAL_TYPE )
    @Column(name = "Coefficiente")
    public Double coefficiente;
    
    @ToInput
    @ToView
    @ValidField(empty = false, type = ValidField.INT_VALIDATION)
    @TextRenderer(type =TextRenderer.INT_TYPE )
    @Column(name = "Priorita")
    public Integer priorita;
    
    @ToInput
    @ToView
    @ValidField(empty = false, type = ValidField.INT_VALIDATION)
    @TextRenderer(type =TextRenderer.INT_TYPE )
    @Column(name = "patch")
    public Integer patch;

    @Override
    public String toString() {
        return "MappingDetail[ idItem=" + id + " ]";
    }
    
    
    /**
     * Tabelle di decodifica
     */
    
    
    @Entity
    @Table(name = "aziende")
    public static class _Azienda implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "IdAzienda")
        public Integer id;

        @ToList(ordered = true, defaultOrdered = true)
        @Column(name = "Descrizione")
        public String descrizione;

        @Column(name = "SistemaInformativo")
        public String si;

    }
    
    @OneToOne
    @JoinColumn(name = "brand", insertable = false, updatable = false)
    public _Marchio _marchio;    

    @Entity
    @Table(name = "marchi")
    public static class _Marchio implements Serializable {

        @Id
        @Column(name = "id")
        public Integer id;

        @Column(name = "descrizione")
        public String descrizione;
    }
    
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "CentroCosto", referencedColumnName = "CodiceCentro", insertable = false, updatable = false),
        @JoinColumn(name = "idAzienda", referencedColumnName = "idAzienda", insertable = false, updatable = false)
    })
    public _CentriCosto _centroCosto;

    @Entity
    @Table(name = "centricosto")
    public static class _CentriCosto implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "IdCentro")
        public Integer id;

        @Column(name = "IdAzienda")
        public Integer idAzienda;

        @Column(name = "DescrizioneCentro")
        public String descrizione;

        @Column(name = "CodiceCentro")
        public String codice;
    }
    
    @OneToOne
    @JoinColumn(name = "Item", insertable = false, updatable = false)
    public _Item parent;

    @Entity
    @Table(name = "anagraficaitem")
    public static class _Item implements Serializable {

        @Id
        @Column(name = "CodiceItem")
        public String id;

        @Column(name = "TipoItem")
        public String tipoItem;
    }
    
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "sottoconto", referencedColumnName = "CodiceConto", insertable = false, updatable = false),
        @JoinColumn(name = "idAzienda", referencedColumnName = "idAzienda", insertable = false, updatable = false)
    })
    public _Sottoconto _sottoconto;

    @Entity
    @Table(name = "sottoconti")
    public static class _Sottoconto implements Serializable {

        @Id
        @Column(name = "idSottoconto")
        public String id;

        @Column(name = "IdAzienda")
        public Integer idAzienda;

        @Column(name = "CodiceConto")
        public String codice;

        @Column(name = "DescrizioneConto")
        public String descrizione;
    }
    
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "voceSpesa", referencedColumnName = "codice", insertable = false, updatable = false),
        @JoinColumn(name = "idAzienda", referencedColumnName = "idAzienda", insertable = false, updatable = false)
    })
    public _VociSpesa _voceSpesa;

    @Entity
    @Table(name = "vocispesa")
    public static class _VociSpesa implements Serializable {

        @Id
        @Column(name = "idVoceSpesa")
        public Integer id;

        @Column(name = "Codice")
        public String codice;

        @Column(name = "Descrizione")
        public String descrizione;

        @Column(name = "IdAzienda")
        public Integer idAzienda;
    }
    
    @OneToOne
    @JoinColumn(name = "ufficio", insertable = false, updatable = false)
    public _Ufficio _ufficio;

    @Entity
    @Table(name = "uffici")
    public static class _Ufficio implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "IdUfficio")
        public String id;

        @Column(name = "NomeUfficio")
        public String nome;

    }

    @OneToOne
    @JoinColumn(name = "genere", insertable = false, updatable = false)
    public _Genere _genere;
    
    @Entity
    @Table(name = "generi")
    public static class _Genere implements Serializable {

        private static final long serialVersionUID = 1L;
        
        @Id
        @Column(name = "IdGenere")
        public String id;

        @Column(name = "NomeGenere")
        public String nome;
    }
    
    @OneToOne
    @JoinColumn(name = "tipoProdotto", insertable = false, updatable = false)
    public _TipoProdotto _tipoProdotto;
    
    @Entity
    @Table(name = "tipoprodotto")
    public static class _TipoProdotto implements Serializable {

        private static final long serialVersionUID = 1L;
        
        @Id
        @Column(name = "IdProdotto")
        public String id;

        @Column(name = "NomeProdotto")
        public String nome;
    }
    
    @OneToOne
    @JoinColumn(name = "stagione", insertable = false, updatable = false)
    public _Stagione _stagione;
    
    @Entity
    @Table(name = "stagioni")
    public static class _Stagione implements Serializable {

        private static final long serialVersionUID = 1L;
        
        @Id
        @Column(name = "IdStagione")
        public String id;

        @Column(name = "NomeStagione")
        public String nome;
    }

}
