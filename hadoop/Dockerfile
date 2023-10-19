# FROM ubuntu:latest
# # Update the package manager
# RUN apt update

# # Install Java (OpenJDK)
# RUN apt install -y openjdk-8-jdk 

# # Install Maven
# RUN apt install maven -y

# CMD ["/bin/bash"]
# Use an Ubuntu base image
FROM ubuntu:20.04

# Set environment variables
ENV HADOOP_HOME /opt/hadoop
ENV PATH $PATH:$HADOOP_HOME/bin
# ENV MAVEN_HOME /opt/maven
# ENV PATH $PATH:$MAVEN_HOME/bin

# Install Java, Hadoop, and Maven dependencies
RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    ssh \
    rsync \
    wget \
    maven \
    && rm -rf /var/lib/apt/lists/

# Install Maven
# RUN wget -qO- https://archive.apache.org/dist/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz | tar -xz -C /opt/ \
#     && mv /opt/apache-maven-3.8.1 $MAVEN_HOME \
#     && ln -s $MAVEN_HOME/bin/mvn /usr/local/bin/mvn

# Create Hadoop directory and set up SSH keys
RUN mkdir -p $HADOOP_HOME && \
    ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa && \
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys && \
    chmod 0600 ~/.ssh/authorized_keys

# Copy Hadoop distribution from the host
COPY hadoop-3.3.6.tar.gz /tmp/hadoop.tar.gz

# Extract and configure Hadoop
RUN tar -xzf /tmp/hadoop.tar.gz -C /opt \
    && mv /opt/hadoop-3.3.6/* $HADOOP_HOME \
    && rm /tmp/hadoop.tar.gz

# Set up Hadoop environment variables
# RUN echo "export HADOOP_HOME=$HADOOP_HOME" >> /etc/profile \
#     && echo "export PATH=$PATH" >> /etc/profile \
#     && source /etc/profile

# Expose Hadoop ports (modify as needed)
EXPOSE 9000 50010 50020 50070 50075 50470 8020 9001 9870 19888

# Start an interactive Bash shell by default
CMD ["/bin/bash"]

