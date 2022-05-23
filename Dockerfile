FROM ubuntu
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer

ENV PROJECT_HOME /usr/src
RUN mkdir -p $PROJECT_HOME/activator $PROJECT_HOME/app

WORKDIR $PROJECT_WORKPLACE/activator

RUN wget http://downloads.typesafe.com/typesafe-activator/1.3.10/typesafe-activator-1.3.10.zip && \
    unzip typesafe-activator-1.3.10.zip

ENV PATH $PROJECT_HOME/activator/activator-dist-1.3.10/bin:$PATH
ENV PATH $PROJECT_WORKPLACE/build/target/universal/stage/bin:$PATH

COPY . $PROJECT_HOME/app

WORKDIR $PROJECT_HOME/app

EXPOSE 9000