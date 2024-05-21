create database Simulacoes --cria banco

use Simulacoes --executa o banco

--CRIAÇÃO DE TABELAS:
create table Emissor --cria tabela emissor
(	--Definição de campos
	nm_emissor int identity(1,1) primary key, 
	Frequencia float not null,
	Velocidade_Emissor float,
	Potencia float not null,
	Velocidade_Onda float not null
)

create table Ouvinte --cria tabela ouvinte
(	--Definição de campos
	nm_ouvinte int identity(1,1) primary key,
	Velocidade_ouvinte float
)

create table Simulacao --cria tabela Simulação
(	--Definição de campos
	Fk_Emissor_nm_emissor int not null
		foreign key references Emissor,
	Fk_Ouvinte_nm_ouvinte int not null
		foreign key references Ouvinte,
	Distancia float not null,
	Frequencia_final float not null,
	Frequencia_inicial float not null,
	audio varbinary(max),
	primary key (Fk_Emissor_nm_emissor, Fk_Ouvinte_nm_ouvinte)
	
)



--CRIAÇÃO DE TRIGGERS:

--criação de uma trigger para insert em Emissor
create or alter trigger tgr_Insert_Emissor on Emissor
for insert as
begin
	--Definição de variaveis
	declare @frequencia float = (select Frequencia from inserted)
	declare @velocidadeEm float = (select Velocidade_Emissor from inserted)
	declare @potencia float = (select Potencia from inserted)
	declare @velocidadeOn float = (select Velocidade_Onda from inserted)

	--Validação que não permite inserção de números negativos
	if (@frequencia < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	if (@velocidadeEm < 0)
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
	declare @frequenciaFi float = (select Frequencia_final from inserted)
	declare @frequenciaini float = (select Frequencia_inicial from inserted)
	
	--Validação que não permite inserção de números negativos
	if (@distancia < 0)
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
create or alter trigger tgr_Insert_Ouvinte on Ouvinte
for insert as
begin
	--Definição de variaveis
	declare @VelocidadeOu float = (select Velocidade_ouvinte from inserted)
	
	--Validação que não permite inserção de números negativos
	if (@VelocidadeOu < 0)
	begin
		raiserror('Não é possível armazernar um valor negativo!', 1, 16)
		Rollback tran
		return
	end
	
end

--criação de uma trigger para deletar todos os dados referente a uma simulação
create or alter trigger tgr_Delete_All on Emissor
instead of delete as
begin
	--Declaração de variaveis
	declare @nm int = (select nm_emissor from deleted)
	--Deleta dados em tabelas
	delete from Simulacao where @nm = Fk_Emissor_nm_emissor
	delete from Ouvinte where @nm= nm_ouvinte
	delete from Emissor where @nm = nm_emissor
end

--Criação de uma SP para inserir dados em EMISSOR
create or alter procedure sp_insert_Emissor (@frequencia float, @VelocidadeEm float,
					     @potencia float, @VelocidadeOn float) as
begin
	insert into Emissor values (@frequencia, @VelocidadeEm, @potencia, @VelocidadeOn)
end

--Criação de uma SP para inserir dados em Simulação
create or alter procedure sp_insert_Simulacao (@Distancia float, @Frequenciafi float, @Frequenciaini float, @audio varbinary(max)) as
begin
	declare @nm int 
	
	set @nm = (select isnull(max(nm_emissor),1) from Emissor)
	
	insert into Simulacao values (@nm, @nm, @Distancia, @Frequenciafi, @Frequenciaini, @audio)
end

--Criação de uma SP para inserir dados em Ouvinte
create or alter procedure sp_insert_Simulacao (@velocidadeOu float) as
begin
	insert into Ouvinte values (@velocidadeOu)
end

