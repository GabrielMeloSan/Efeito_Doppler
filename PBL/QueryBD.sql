create database Simulacoes --cria banco

use Simulacoes --executa o banco

--CRIAÇÃO DE TABELAS:
create table Emissor --cria tabela emissor
(	--Definição de campos
	nm_emissor int identity(1,1) primary key, 
	Frequencia float not null,
	Velocidade_Relativa float,
	Potencia float not null,
	Velocidade_Onda float not null
)

-- Descontinuado
-- create table Ouvinte --cria tabela ouvinte
-- (	--Definição de campos
-- 	nm_ouvinte int identity(1,1) primary key,
-- 	Velocidade_ouvinte float
-- )

create table Simulacao --cria tabela Simulação
(	--Definição de campos
	Fk_Emissor_nm_emissor int not null
		foreign key references Emissor,
	/* Fk_Ouvinte_nm_ouvinte int not null
		foreign key references Ouvinte, [descontinuado]*/
	Distancia float not null,
    Tempo float not null,
	Intensidade float not null,
	Frequencia_final float not null,
	Frequencia_inicial float not null,
    NomeAudio varchar,
	Audio varbinary(max),
	primary key (Fk_Emissor_nm_emissor /*,Fk_Ouvinte_nm_ouvinte*/)
	
)



--CRIAÇÃO DE TRIGGERS:

--criação de uma trigger para insert em Emissor
create or alter trigger tgr_Insert_Emissor on Emissor
for insert as
begin
	--Definição de variaveis
	declare @frequencia float = (select Frequencia from inserted)
	declare @velocidadeRel float = (select Velocidade_Relativa from inserted)
	declare @potencia float = (select Potencia from inserted)
	declare @velocidadeOn float = (select Velocidade_Onda from inserted)

	--Validação que não permite inserção de números negativos
	if (@frequencia < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@velocidadeRel < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@potencia < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@velocidadeOn < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end

end


--criação de uma trigger para insert em Simulação
create or alter trigger tgr_Insert_Simulacao on Simulacao
for insert as
begin
	--Definição de variaveis
	declare @distancia float = (select Distancia from inserted)
	declare @tempo float = (select Tempo from inserted)
	declare @intensidade float = (select Intensidade from inserted)
	declare @frequenciaFi float = (select Frequencia_final from inserted)
	declare @frequenciaini float = (select Frequencia_inicial from inserted)
	
	--Validação que não permite inserção de números negativos
	if (@distancia < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@tempo < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@intensidade < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@frequenciaFi < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@frequenciaini < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end


end

--criação de uma trigger para insert em Ouvinte
-- create or alter trigger tgr_Insert_Ouvinte on Ouvinte
-- for insert as
-- begin
-- 	--Definição de variaveis
-- 	declare @VelocidadeOu float = (select Velocidade_ouvinte from inserted)
-- 	
-- 	--Validação que não permite inserção de números negativos
-- 	if (@VelocidadeOu < 0)
-- 	begin
-- 		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
-- 		Rollback tran
-- 		return
-- 	end
-- 	
-- end

--criação de uma trigger para deletar todos os dados referente a uma simulação
create or alter trigger tgr_Delete_All on Emissor
instead of delete as
begin
	--Declaração de variaveis
	declare @nm int = (select nm_emissor from deleted)
	--Deleta dados em tabelas
	delete from Simulacao where @nm = Fk_Emissor_nm_emissor
	-- delete from Ouvinte where @nm= nm_ouvinte
	delete from Emissor where @nm = nm_emissor
end

--Criação de uma SP para inserir dados em EMISSOR
create or alter procedure sp_insert_Emissor (@frequencia float, @VelocidadeRel float,
					     @potencia float, @VelocidadeOn float) as
begin
	insert into Emissor values (@frequencia, @VelocidadeRel, @potencia, @VelocidadeOn)
end

--Criação de uma SP para inserir dados em Simulação
create or alter procedure sp_insert_Simulacao (@Distancia float, @Tempo float, @Intensidade float, @Frequenciafi float, @Frequenciaini float, @nome varchar, @audio varbinary(max)) as
begin
	declare @nm int 
	
	set @nm = (select isnull(max(nm_emissor),1) from Emissor)
	
	insert into Simulacao values (@nm, /*@nm,*/ @Distancia, @Tempo, @Intensidade, @Frequenciafi, @Frequenciaini, @nome, @audio)
end

--Criação de uma SP para inserir dados em Ouvinte
-- create or alter procedure sp_insert_Simulacao (@velocidadeOu float) as
-- begin
-- 	insert into Ouvinte values (@velocidadeOu)
-- end

--Criação de uma SP para deletar todos os dados de uma simulação através da trigger
create or alter procedure sp_delete_all (@nm int) as begin delete from Emissor where nm_emissor = @nm end

-- SPs de select
-- SP para selecionar todos os registros
create or alter procedure sp_Select_All as begin
	select e.*, s.Distancia, s.Tempo, s.Intensidade, s.Frequencia_inicial, s.Frequencia_final, s.NomeAudio from Emissor e
	left join Simulacao s on s.Fk_Emissor_nm_emissor = e.nm_emissor
end

-- SP para selecionar um registro específico
create or alter procedure sp_Select_ID (@id int) as begin
	select e.*, s.Distancia, s.Tempo, s.Intensidade, s.Frequencia_inicial, s.Frequencia_final, s.NomeAudio from Emissor e
	left join Simulacao s on s.Fk_Emissor_nm_emissor = e.nm_emissor
	where e.nm_emissor = @id
end

-- SP para selecionar um áudio
create or alter procedure sp_Select_Audio (@id int) as begin
	select NomeAudio, Audio from Simulacao
	where Fk_Emissor_nm_emissor = @id
end

